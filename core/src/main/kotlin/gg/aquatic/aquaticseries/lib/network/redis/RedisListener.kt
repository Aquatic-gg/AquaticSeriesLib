package gg.aquatic.aquaticseries.lib.network.redis

import redis.clients.jedis.JedisPubSub

class RedisListener(private val handler: RedisHandler): JedisPubSub() {

    override fun onMessage(channel: String?, message: String?) {
        if (channel != handler.settings.channel) {
            return
        }
        if (message == null) {
            return
        }

        val packet = handler.networkPacketListener.serializePacket(message) ?: return
        handler.networkPacketListener.handle(packet)
    }

}