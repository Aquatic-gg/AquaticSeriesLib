package gg.aquatic.aquaticseries.lib.network.redis.packet

import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketHandler
import gg.aquatic.aquaticseries.lib.network.SignedNetworkPacket
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.concurrent.CompletableFuture

object RedisServerPingPacketHandler: NetworkPacketHandler<RedisServerPingPacket> {
    override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
        return CompletableFuture.completedFuture(null)
    }

    val serializersModule = SerializersModule {
        polymorphic(NetworkPacket::class) {
            subclass(RedisServerPingPacket::class)
        }
    }
}