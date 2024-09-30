package gg.aquatic.aquaticseries.lib.network.redis.packet

import com.google.gson.Gson
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketHandler
import gg.aquatic.aquaticseries.lib.network.PacketSerializer
import gg.aquatic.aquaticseries.lib.network.SignedNetworkPacket
import gg.aquatic.aquaticseries.lib.network.event.ServerNetworkDisconnectEvent
import gg.aquatic.aquaticseries.lib.network.redis.RedisHandler
import gg.aquatic.aquaticseries.lib.util.call
import java.util.concurrent.CompletableFuture

class RedisServerDisconnectPacket(override val channel: String) : NetworkPacket() {


    fun serializer(): PacketSerializer<RedisServerDisconnectPacket> = Serializer
    override fun serialize(): String {
        return this.serializer().serialize(this)
    }
    object Serializer: PacketSerializer<RedisServerDisconnectPacket>() {
        override fun serialize(packet: RedisServerDisconnectPacket): String {
            return Gson().toJson(packet, RedisServerDisconnectPacket::class.java)
        }

        override fun deserialize(json: String): RedisServerDisconnectPacket {
            return Gson().fromJson(json, RedisServerDisconnectPacket::class.java)
        }
    }

    object Handler: NetworkPacketHandler<RedisServerDisconnectPacket> {
        override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
            val response = CompletableFuture.completedFuture("")

            val npl = AquaticSeriesLib.INSTANCE.networkPacketListener ?: return response
            val redisAdapter = npl.adapter as? RedisHandler ?: return response

            redisAdapter.connectedServers -= packet.sentFrom
            ServerNetworkDisconnectEvent(packet.sentFrom).call()

            return CompletableFuture.completedFuture("")
        }
    }

    override val id: String = "REDIS_SERVER_DISCONNECT"

}