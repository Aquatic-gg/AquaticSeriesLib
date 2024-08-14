package gg.aquatic.aquaticseries.lib.inventory.lib.component

import gg.aquatic.aquaticseries.lib.inventory.lib.SlotSelection
import gg.aquatic.aquaticseries.lib.inventory.lib.event.ComponentClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory
import java.util.function.Consumer

abstract class MenuComponent {

    abstract var slotSelection: SlotSelection
    abstract var onClick: Consumer<ComponentClickEvent>?
    abstract var priority: Int

    abstract fun onViewLoad(inventory: CustomInventory)
    abstract fun onClick(event: ComponentClickEvent)


}