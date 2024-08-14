package gg.aquatic.aquaticseries.lib.inventory.lib.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import gg.aquatic.aquaticseries.lib.inventory.lib.component.MenuComponent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class ComponentOverrideEvent(
    val inventory: CustomInventory,
    val previousComponent: MenuComponent,
    val newComponent: MenuComponent
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