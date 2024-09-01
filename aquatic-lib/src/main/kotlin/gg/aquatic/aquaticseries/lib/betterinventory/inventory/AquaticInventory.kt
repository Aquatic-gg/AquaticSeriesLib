package gg.aquatic.aquaticseries.lib.betterinventory.inventory

import gg.aquatic.aquaticseries.lib.betterinventory.component.ComponentHandler
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class AquaticInventory: InventoryHolder {

    val componentHandler = ComponentHandler(this)
    val content = ArrayList<ItemStack?>()

    override fun getInventory(): Inventory {
        TODO("Not yet implemented")
    }
}