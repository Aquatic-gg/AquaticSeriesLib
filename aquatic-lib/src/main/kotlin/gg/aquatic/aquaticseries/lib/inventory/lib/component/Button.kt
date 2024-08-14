package gg.aquatic.aquaticseries.lib.inventory.lib.component

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import gg.aquatic.aquaticseries.lib.inventory.lib.SlotSelection
import gg.aquatic.aquaticseries.lib.inventory.lib.event.ComponentClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.PersonalizedInventory
import gg.aquatic.aquaticseries.lib.item.CustomItem
import gg.aquatic.aquaticseries.lib.updatePlaceholders
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import java.util.function.Consumer

class Button(
    var itemStack: ItemStack,
    override var slotSelection: SlotSelection,
    override var onClick: Consumer<ComponentClickEvent>?,
    override var priority: Int
) : MenuComponent() {
    override fun onViewLoad(inventory: CustomInventory) {
        for (slot in slotSelection.slots) {
            if (inventory is PersonalizedInventory) {
                inventory.setItem(slot, itemStack.clone().updatePlaceholders(inventory.player, Placeholders()))
                continue
            }
            inventory.setItem(slot, itemStack)
        }
    }

    override fun onClick(event: ComponentClickEvent) {
        onClick?.accept(event)
    }

    companion object {

        fun fromConfig(section: ConfigurationSection): Button {
            val item = CustomItem.loadFromYaml(section)
            val slots = section.getIntegerList("slots")
            val priority = section.getInt("priority", 0)

            return Button(item!!.getItem(), SlotSelection.of(slots), null, priority)
        }

    }
}