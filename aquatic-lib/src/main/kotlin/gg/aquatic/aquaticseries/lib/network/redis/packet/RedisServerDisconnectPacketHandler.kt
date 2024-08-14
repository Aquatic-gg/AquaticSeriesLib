package gg.aquatic.aquaticseries.lib.network.redis.packet

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketHandler
import gg.aquatic.aquaticseries.lib.network.SignedNetworkPacket
import gg.aquatic.aquaticseries.lib.network.event.ServerNetworkDisconnectEvent
import gg.aquatic.aquaticseries.lib.network.redis.RedisHandler
import gg.aquatic.aquaticseries.lib.util.call
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.concurrent.CompletableFuture

object RedisServerDisconnectPacketHandler: NetworkPacketHandler<RedisServerDisconnectPacket> {
    override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
        val response = CompletableFuture.completedFuture("")

        val npl = AbstractAquaticSeriesLib.INSTANCE.networkPacketListener ?: return response
        val redisAdapter = npl.adapter as? RedisHandler ?: return response

        redisAdapter.connectedServers -= packet.sentFrom
        ServerNetworkDisconnectEvent(packet.sentFrom).call()

        return CompletableFuture.completedFuture("")
    }

    val serializersModule = SerializersModule {
        polymorphic(NetworkPacket::class) {
            subclass(RedisServerDisconnectPacket::class)
        }
    }
}