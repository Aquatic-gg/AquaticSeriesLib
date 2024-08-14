package gg.aquatic.aquaticseries.lib.network

import java.util.concurrent.CompletableFuture

abstract class NetworkAdapter {

    abstract val serverName: String
    abstract fun send(packet: NetworkPacket): CompletableFuture<NetworkResponse>
    abstract fun connectedServer(): List<String>

    val requests = HashMap<NetworkRequest, CompletableFuture<NetworkResponse>>()

}