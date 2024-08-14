package gg.aquatic.aquaticseries.lib.network

interface NetworkPacketHandler<T: NetworkPacket> {

    fun handle(packet: NetworkPacket)

}