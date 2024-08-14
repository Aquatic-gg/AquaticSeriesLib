package gg.aquatic.aquaticseries.lib.network

import kotlinx.serialization.Serializable

@Serializable
class SignedNetworkPacket(
    val packet: NetworkPacket,
    val sentFrom: String
) {
}