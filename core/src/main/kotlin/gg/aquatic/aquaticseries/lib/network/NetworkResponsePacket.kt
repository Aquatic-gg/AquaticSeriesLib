package gg.aquatic.aquaticseries.lib.network

import kotlinx.serialization.Serializable

@Serializable
class NetworkResponsePacket(
    override val channel: String,
    val response: NetworkResponse
) : NetworkPacket() {
}