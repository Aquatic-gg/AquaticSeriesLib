package gg.aquatic.aquaticseries.lib.network.redis

import gg.aquatic.aquaticseries.lib.network.*
import org.bukkit.scheduler.BukkitRunnable
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class RedisHandler(
    val networkPacketListener: NetworkPacketListener,
    val settings: RedisNetworkSettings, override val serverName: String
): NetworkAdapter {

    val requests = HashMap<NetworkRequest, CompletableFuture<NetworkResponse>>()
    private lateinit var jedisPool: JedisPool

    fun setup(): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()
        jedisPool = JedisPool(
            JedisPoolConfig(),
            settings.ip,
            settings.port,
            settings.user,
            settings.password
        )

        object : BukkitRunnable() {
            override fun run() {
                Thread {
                    execute {
                        it.subscribe(RedisListener(this@RedisHandler), settings.channel)
                    }
                }.start()

                future.complete(null)
            }
        }
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

        publishAsync(settings.channel,networkPacketListener.deserializePacket(signedPacket) ?: return CompletableFuture.completedFuture(
            NetworkResponse(NetworkResponse.Status.ERROR, null)
        ))
        requests[NetworkRequest(packet)] = future

        return future
    }

}