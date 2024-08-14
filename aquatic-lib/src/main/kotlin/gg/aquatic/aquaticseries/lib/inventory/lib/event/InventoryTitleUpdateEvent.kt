package gg.aquatic.aquaticseries.lib.inventory.lib.event

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class InventoryTitleUpdateEvent(
    val inventory: CustomInventory,
    val previousTitle: AquaticString?,
    val newTitle: AquaticString
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