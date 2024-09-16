package gg.aquatic.aquaticseries.lib.network

abstract class NetworkPacket {

    abstract val id: String
    abstract val channel: String

    abstract fun serialize(): String

}