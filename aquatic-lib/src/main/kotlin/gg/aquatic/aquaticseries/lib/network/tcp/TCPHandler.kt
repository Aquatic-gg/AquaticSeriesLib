package gg.aquatic.aquaticseries.lib.network.tcp

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.network.*
import org.bukkit.scheduler.BukkitRunnable
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.CompletableFuture
import kotlin.collections.HashMap

class TCPHandler(
    val networkPacketListener: NetworkPacketListener,
    val settings: TCPNetworkSettings, override val serverName: String
): NetworkAdapter() {

    val server = ServerSocket(settings.port)
    val clients = HashMap<String, Socket>()

    init {
        setup()
    }

    private fun setup() {
        val server = server.accept()
        Thread {
            val iS = server.getInputStream()
            while (true) {
                val bytes = iS.readBytes()
                val json = String(bytes)
                val signedPacket = networkPacketListener.serializePacket(json) ?: continue
                val packet = signedPacket.packet

                networkPacketListener.handle(signedPacket).thenAccept { response ->
                    if (packet !is NetworkResponsePacket) {
                        send(NetworkResponsePacket(signedPacket.sentFrom, response))
                    }
                }
            }
        }

        object : BukkitRunnable() {
            override fun run() {
                for ((name, serverInfo) in settings.servers) {
                    if (clients.containsKey(name)) {
                        val client = clients[name] ?: continue
                        if (client.isClosed || !client.isConnected) {
                            clients.remove(name)
                        }
                        continue
                    }
                    try {
                        val client = Socket(serverInfo.ip, serverInfo.port)
                        if (!(client.isClosed || !client.isConnected)) {
                            clients[name] = client
                        }
                    } catch (_: Exception) {
                        continue
                    }
                }

                for ((request, future) in requests) {
                    if (request.timestamp > System.currentTimeMillis() + 10000) {
                        requests.remove(request)
                        future.complete(NetworkResponse(NetworkResponse.Status.ERROR, null))
                    }
                }
            }
        }.runTaskTimer(AbstractAquaticSeriesLib.INSTANCE.plugin, 40, 40)

    }

    override fun send(packet: NetworkPacket): CompletableFuture<NetworkResponse> {
        val signedPacket = SignedNetworkPacket(
            packet,
            serverName
        )
        val future = CompletableFuture<NetworkResponse>()
        val server = packet.channel
        val bytes = networkPacketListener.deserializePacket(signedPacket)?.toByteArray() ?: return CompletableFuture.completedFuture(
            NetworkResponse(NetworkResponse.Status.ERROR, null)
        )

        val client = clients[server] ?: return CompletableFuture.completedFuture(
            NetworkResponse(NetworkResponse.Status.ERROR, null)
        )

        try {
            client.getOutputStream().write(bytes)
            requests[NetworkRequest(packet)] = future
        } catch (_: Exception) {
            return CompletableFuture.completedFuture(
                NetworkResponse(NetworkResponse.Status.ERROR, null)
            )
        }
        return future
    }

    override fun connectedServer(): List<String> {
        return clients.keys.toList()
    }
}