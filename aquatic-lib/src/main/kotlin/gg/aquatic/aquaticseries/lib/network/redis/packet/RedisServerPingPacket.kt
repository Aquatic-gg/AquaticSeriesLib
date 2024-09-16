package gg.aquatic.aquaticseries.lib.network.redis.packet

import com.google.gson.Gson
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketHandler
import gg.aquatic.aquaticseries.lib.network.PacketSerializer
import gg.aquatic.aquaticseries.lib.network.SignedNetworkPacket
import java.util.concurrent.CompletableFuture

class RedisServerPingPacket(override val channel: String) : NetworkPacket() {

    fun serializer(): PacketSerializer<RedisServerPingPacket> = Serializer
    override fun serialize(): String {
        return this.serializer().serialize(this)
    }
    object Handler : NetworkPacketHandler<RedisServerPingPacket> {
        override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
            return CompletableFuture.completedFuture(null)
        }
    }

    object Serializer : PacketSerializer<RedisServerPingPacket>() {
        override fun serialize(packet: RedisServerPingPacket): String {
            return Gson().toJson(packet, RedisServerPingPacket::class.java)
        }

        override fun deserialize(json: String): RedisServerPingPacket {
            return Gson().fromJson(json, RedisServerPingPacket::class.java)
        }
    }

    override val id: String = "REDIS_SERVER_PING"

}