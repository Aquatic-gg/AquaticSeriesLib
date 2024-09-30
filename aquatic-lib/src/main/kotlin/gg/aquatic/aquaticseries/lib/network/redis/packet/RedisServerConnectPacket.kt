package gg.aquatic.aquaticseries.lib.network.redis.packet

import com.google.gson.Gson
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketHandler
import gg.aquatic.aquaticseries.lib.network.PacketSerializer
import gg.aquatic.aquaticseries.lib.network.SignedNetworkPacket
import gg.aquatic.aquaticseries.lib.network.event.ServerNetworkConnectEvent
import gg.aquatic.aquaticseries.lib.network.redis.RedisHandler
import gg.aquatic.aquaticseries.lib.util.call
import gg.aquatic.aquaticseries.lib.util.runSync
import java.util.concurrent.CompletableFuture

class RedisServerConnectPacket(override val channel: String) : NetworkPacket() {

    override val id: String = "REDIS_SERVER_CONNECT"
    fun serializer(): PacketSerializer<RedisServerConnectPacket> = Serializer

    override fun serialize(): String {
        return this.serializer().serialize(this)
    }
    object Serializer: PacketSerializer<RedisServerConnectPacket>() {
        override fun serialize(packet: RedisServerConnectPacket): String {
            return Gson().toJson(packet, RedisServerConnectPacket::class.java)
        }

        override fun deserialize(json: String): RedisServerConnectPacket {
            return Gson().fromJson(json, RedisServerConnectPacket::class.java)
        }
    }

    object Handler: NetworkPacketHandler<RedisServerConnectPacket> {
        override fun handle(packet: SignedNetworkPacket): CompletableFuture<String> {
            val response = CompletableFuture.completedFuture("")

            val npl = AquaticSeriesLib.INSTANCE.networkPacketListener ?: return response
            val redisAdapter = npl.adapter as? RedisHandler ?: return response

            redisAdapter.connectedServers += packet.sentFrom
            runSync {
                ServerNetworkConnectEvent(packet.sentFrom).call()
            }

            return response
        }
    }


}