package gg.aquatic.aquaticseries.lib.network.redis

import gg.aquatic.aquaticseries.lib.network.NetworkResponsePacket
import redis.clients.jedis.JedisPubSub

class RedisListener(private val handler: RedisHandler): JedisPubSub() {

    override fun onMessage(channel: String?, message: String?) {
        if (channel != handler.settings.channel) {
            return
        }
        if (message == null) {
            return
        }

        val signedPacket = handler.networkPacketListener.serializePacket(message) ?: return
        val packet = signedPacket.packet

        handler.networkPacketListener.handle(signedPacket).thenAccept { response ->
            if (packet !is NetworkResponsePacket) {
                handler.send(NetworkResponsePacket(signedPacket.sentFrom, response))
            }
        }
    }

}