package gg.aquatic.aquaticseries.lib.inventory.lib.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryInteractEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class CustomInventoryInteractEvent(
    val inventory: CustomInventory,
    val originalEvent: InventoryInteractEvent
): Event() {
    companion object {
        private val HANDLERS = HandlerList()

        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}