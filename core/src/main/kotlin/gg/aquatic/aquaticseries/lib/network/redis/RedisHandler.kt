package gg.aquatic.aquaticseries.lib.network.redis

import gg.aquatic.aquaticseries.lib.network.NetworkAdapter
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketListener
import org.bukkit.scheduler.BukkitRunnable
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class RedisHandler(
    val networkPacketListener: NetworkPacketListener,
    val settings: RedisNetworkSettings
): NetworkAdapter {

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

    fun publishAsync(channel: String?, message: String): CompletableFuture<Void> {
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

    override fun send(packet: NetworkPacket) {
        publishAsync(settings.channel,networkPacketListener.deserializePacket(packet) ?: return)
    }

}