package gg.aquatic.aquaticseries.lib.network

abstract class PacketSerializer<T: NetworkPacket> {

    abstract fun serialize(packet: T): String

    abstract fun deserialize(json: String): T
}