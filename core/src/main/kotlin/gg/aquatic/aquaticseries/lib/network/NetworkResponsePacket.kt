package gg.aquatic.aquaticseries.lib.network

import com.google.gson.Gson
import java.util.concurrent.CompletableFuture

class NetworkResponsePacket(
    override val channel: String,
    val response: NetworkResponse
) : NetworkPacket() {
    override val id: String = "NETWORK_RESPONSE"
    fun serializer(): PacketSerializer<NetworkResponsePacket> = Serializer
    override fun serialize(): String {
        return this.serializer().serialize(this)
    }

    object Serializer: PacketSerializer<NetworkResponsePacket>() {
        override fun serialize(packet: NetworkResponsePacket): String {
            return Gson().toJson(packet)
        }

        override fun deserialize(json: String): NetworkResponsePacket {
            return Gson().fromJson(json, NetworkResponsePacket::class.java)
        }
    }

    object Handler : NetworkPacketHandler<NetworkResponsePacket> {
        override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
            return CompletableFuture.completedFuture("")
        }

    }
}