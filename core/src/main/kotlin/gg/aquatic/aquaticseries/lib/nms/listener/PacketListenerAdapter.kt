package gg.aquatic.aquaticseries.lib.nms.listener

import org.bukkit.entity.Player

interface PacketListenerAdapter {

    fun inject(player: Player)
    fun eject(player: Player)

    fun registerListener(
        listener: AbstractPacketListener
    )

}