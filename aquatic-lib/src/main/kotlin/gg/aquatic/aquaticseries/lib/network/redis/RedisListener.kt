package gg.aquatic.aquaticseries.lib.network.redis

import gg.aquatic.aquaticseries.lib.logger.type.InfoLogger
import gg.aquatic.aquaticseries.lib.network.NetworkResponsePacket
import redis.clients.jedis.JedisPubSub

class RedisListener(private val handler: RedisHandler): JedisPubSub() {

    override fun onMessage(channel: String?, message: String?) {
        InfoLogger.send("Received a packet: $channel : $message")
        if (channel != handler.settings.channel) {
            InfoLogger.send("Channel $channel does not equal to ${handler.settings.channel}")
            return
        }
        if (message == null) {
            return
        }

        val signedPacket = handler.networkPacketListener.serializePacket(message)
        if (signedPacket == null) {
            InfoLogger.send("Packet could not be serialized!")
            return
        }

        val sentTo = signedPacket.packet.channel

        if (sentTo != handler.serverName) {
            InfoLogger.send("Packet is not meant to be sent to this server! $sentTo != ${handler.serverName}")
            return
        }

        val packet = signedPacket.packet

        handler.networkPacketListener.handle(signedPacket).thenAccept { response ->
            if (packet !is NetworkResponsePacket) {
                handler.send(NetworkResponsePacket(signedPacket.sentFrom, response))
            }
        }
    }

}