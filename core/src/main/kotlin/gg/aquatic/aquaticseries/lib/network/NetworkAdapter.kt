package gg.aquatic.aquaticseries.lib.network

interface NetworkAdapter {

    fun send(packet: NetworkPacket)

}