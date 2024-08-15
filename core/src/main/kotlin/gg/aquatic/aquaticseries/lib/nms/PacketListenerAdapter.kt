package gg.aquatic.aquaticseries.lib.nms

import org.bukkit.entity.Player

interface PacketListenerAdapter {

    fun inject(player: Player)
    fun eject(player: Player)

}