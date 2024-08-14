package gg.aquatic.aquaticseries.lib.network.redis.packet

import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketHandler
import gg.aquatic.aquaticseries.lib.network.SignedNetworkPacket
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.concurrent.CompletableFuture

object RedisServerDisconnectPacketHandler: NetworkPacketHandler<RedisServerDisconnectPacket> {
    override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
        return CompletableFuture.completedFuture("")
    }

    val serializersModule = SerializersModule {
        polymorphic(NetworkPacket::class) {
            subclass(RedisServerDisconnectPacket::class)
        }
    }
}