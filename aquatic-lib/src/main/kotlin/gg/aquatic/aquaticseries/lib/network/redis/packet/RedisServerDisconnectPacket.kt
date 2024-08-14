package gg.aquatic.aquaticseries.lib.network.redis.packet

import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import kotlinx.serialization.Serializable

@Serializable
class RedisServerDisconnectPacket(override val channel: String) : NetworkPacket() {
}