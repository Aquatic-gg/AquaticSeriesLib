package gg.aquatic.aquaticseries.lib.network

import java.util.*

class NetworkRequest(
    val packet: NetworkPacket,
) {

    val timestamp = System.currentTimeMillis()
    val uuid = UUID.randomUUID()

}