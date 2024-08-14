package gg.aquatic.aquaticseries.lib.inventory.lib.component

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

interface ViewOpenComponent {

    fun onViewOpen(inventory: CustomInventory, player: Player)

}