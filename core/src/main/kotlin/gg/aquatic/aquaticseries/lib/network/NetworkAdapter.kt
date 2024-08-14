package gg.aquatic.aquaticseries.lib.network

import java.util.concurrent.CompletableFuture

interface NetworkAdapter {

    val serverName: String
    fun send(packet: NetworkPacket): CompletableFuture<NetworkResponse>

}