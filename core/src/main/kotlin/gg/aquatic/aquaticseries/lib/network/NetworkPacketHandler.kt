package gg.aquatic.aquaticseries.lib.network

import java.util.concurrent.CompletableFuture

interface NetworkPacketHandler<T: NetworkPacket> {

    fun handle(packet: SignedNetworkPacket): CompletableFuture<String>

}