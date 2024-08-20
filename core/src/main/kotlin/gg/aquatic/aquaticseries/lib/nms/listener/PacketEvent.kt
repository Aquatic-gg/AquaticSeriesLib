package gg.aquatic.aquaticseries.lib.nms.listener

import gg.aquatic.aquaticseries.lib.nms.packet.WrappedPacket
import org.bukkit.entity.Player

class PacketEvent<T: WrappedPacket>(
    val player: Player,
    val packet: T
) {

    var cancelled: Boolean = false

}