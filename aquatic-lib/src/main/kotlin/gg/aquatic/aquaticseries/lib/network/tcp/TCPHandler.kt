package gg.aquatic.aquaticseries.lib.network.tcp

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.network.NetworkAdapter
import gg.aquatic.aquaticseries.lib.network.NetworkPacket
import gg.aquatic.aquaticseries.lib.network.NetworkPacketListener
import org.bukkit.scheduler.BukkitRunnable
import java.net.ServerSocket
import java.net.Socket
import kotlin.collections.HashMap

class TCPHandler(
    val networkPacketListener: NetworkPacketListener,
    val settings: TCPNetworkSettings
): NetworkAdapter {

    val server = ServerSocket(settings.port)
    val clients = HashMap<String, Socket>()

    fun setup() {
        val server = server.accept()
        Thread {
            val iS = server.getInputStream()
            while (true) {
                val bytes = iS.readBytes()
                val json = String(bytes)
                val packet = networkPacketListener.serializePacket(json) ?: continue
                networkPacketListener.handle(packet)
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
            }
        }.runTaskTimer(AbstractAquaticSeriesLib.INSTANCE.plugin, 40, 40)

    }

    override fun send(packet: NetworkPacket) {
        val server = packet.channel

        val bytes = networkPacketListener.deserializePacket(packet)?.toByteArray() ?: return

        if (server == null) {
            val toRemove = ArrayList<String>()
            for ((id, value) in clients) {
                try {
                    value.getOutputStream().write(bytes)
                } catch (_: Exception) {
                    toRemove.add(id)
                }
            }
            toRemove.forEach { clients.remove(it) }
        }
    }
}