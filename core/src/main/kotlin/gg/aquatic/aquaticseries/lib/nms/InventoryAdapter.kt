package gg.aquatic.aquaticseries.lib.nms

import org.bukkit.entity.Player

interface InventoryAdapter {

    fun injectPlayer(player: Player)
    fun ejectPlayer(player: Player)

}