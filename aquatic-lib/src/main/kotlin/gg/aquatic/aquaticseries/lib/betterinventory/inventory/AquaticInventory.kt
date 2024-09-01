package gg.aquatic.aquaticseries.lib.betterinventory.inventory

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.betterinventory.component.ComponentHandler
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class AquaticInventory(
    val title: AquaticString,
    val size: Int,
    val inventoryType: InventoryType?
) : InventoryHolder {

    val componentHandler = ComponentHandler(this)
    val content = ArrayList<ItemStack?>()

    private val inventory: Inventory = createInventory()

    private fun createInventory(): Inventory {
        return if (inventoryType != null) AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create(
            title,
            this,
            inventoryType
        )
        else {
            AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create(
                title,
                this,
                size
            )
        }
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    fun tick() {
        componentHandler.tick()
    }
}