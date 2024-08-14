package gg.aquatic.aquaticseries.lib.network.tcp

import gg.aquatic.aquaticseries.lib.network.NetworkAdapter
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketListener
import java.net.ServerSocket
import java.util.*
import kotlin.collections.LinkedHashMap

class TCPHandler(
    val networkPacketListener: NetworkPacketListener,
    val settings: TCPNetworkSettings
): NetworkAdapter {

    val server = ServerSocket(settings.port)

    //val connections = Collections.synchronizedMap<String,ConnectedServer>(LinkedHashMap())

    override fun send(packet: NetworkPacket) {
        //server.channel.accept().write()
    }
}