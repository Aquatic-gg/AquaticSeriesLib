package gg.aquatic.aquaticseries.lib.network.redis

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.network.*
import gg.aquatic.aquaticseries.lib.network.event.ServerNetworkDisconnectEvent
import gg.aquatic.aquaticseries.lib.network.redis.packet.*
import gg.aquatic.aquaticseries.lib.util.call
import gg.aquatic.aquaticseries.lib.util.runSync
import org.bukkit.scheduler.BukkitRunnable
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class RedisHandler(
    val networkPacketListener: NetworkPacketListener,
    val settings: RedisNetworkSettings, override val serverName: String
) : NetworkAdapter() {

    private lateinit var jedisPool: JedisPool

    init {
        setup()

        networkPacketListener.registerPacket(
            RedisServerConnectPacket::class.java,
            RedisServerConnectPacketHandler,
            RedisServerConnectPacketHandler.serializersModule
        )
        networkPacketListener.registerPacket(
            RedisServerDisconnectPacket::class.java,
            RedisServerDisconnectPacketHandler,
            RedisServerDisconnectPacketHandler.serializersModule
        )
        networkPacketListener.registerPacket(
            RedisServerPingPacket::class.java,
            RedisServerPingPacketHandler,
            RedisServerPingPacketHandler.serializersModule
        )
    }

    val connectedServers = mutableListOf<String>()

    private fun setup(): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()
        jedisPool = JedisPool(
            JedisPoolConfig(),
            settings.ip,
            settings.port,
            settings.user,
            settings.password
        )

        runSync {
            Thread {
                execute {
                    it.subscribe(RedisListener(this@RedisHandler), settings.channel)
                }
            }.start()

            future.complete(null)
            settings.servers.forEach { server ->
                send(RedisServerConnectPacket(server)).thenAccept {}
            }
        }

        object : BukkitRunnable() {
            override fun run() {
                for (server in settings.servers) {
                    if (connectedServers.contains(server)) {
                        send(RedisServerPingPacket(server)).thenAccept { response ->
                            if (response.status != NetworkResponse.Status.ERROR) {
                                if (!connectedServers.contains(server)) connectedServers += server
                                return@thenAccept
                            }
                            connectedServers -= server
                            ServerNetworkDisconnectEvent(server).call()
                        }
                        continue
                    }
                    send(RedisServerConnectPacket(server)).thenAccept {}
                }
            }
        }.runTaskTimer(AbstractAquaticSeriesLib.INSTANCE.plugin, 20 * 60, 20 * 60)

        object : BukkitRunnable() {
            override fun run() {
                for ((request, f) in requests) {
                    if (request.timestamp > System.currentTimeMillis() + 10000) {
                        requests.remove(request)
                        f.complete(NetworkResponse(NetworkResponse.Status.ERROR, null))
                    }
                }
            }
        }.runTaskTimer(AbstractAquaticSeriesLib.INSTANCE.plugin, 40, 40)

        return future
    }

    private fun publishAsync(channel: String?, message: String): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()

        CompletableFuture.runAsync {
            execute { jedis ->
                jedis.publish(channel, message)
            }
            future.complete(null)
        }
        return future
    }

    private fun execute(action: Consumer<Jedis>) {
        jedisPool.let {
            it.resource.use { jedis ->
                action.accept(jedis)
            }
        }
    }

    override fun send(packet: NetworkPacket): CompletableFuture<NetworkResponse> {
        val future = CompletableFuture<NetworkResponse>()

        val signedPacket = SignedNetworkPacket(
            packet,
            serverName
        )

        publishAsync(
            settings.channel,
            networkPacketListener.deserializePacket(signedPacket) ?: return CompletableFuture.completedFuture(
                NetworkResponse(NetworkResponse.Status.ERROR, null)
            )
        )
        requests[NetworkRequest(packet)] = future

        return future
    }

    override fun connectedServer(): List<String> {
        return listOf()
    }

}