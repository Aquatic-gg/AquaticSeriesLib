package gg.aquatic.aquaticseries.lib.betterinventory2.component

import gg.aquatic.aquaticseries.lib.betterinventory2.SlotSelection
import gg.aquatic.aquaticseries.lib.betterinventory2.AquaticInventory
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

abstract class InventoryComponent {

    abstract val id: String
    abstract val priority: Int
    abstract val slotSelection: SlotSelection

    abstract val viewConditions: HashMap<Function<Player, Boolean>, InventoryComponent?>
    abstract val failItem: InventoryComponent?
    abstract val onClick: Consumer<InventoryClickEvent>?

    abstract val updateEvery: Int
    abstract val textUpdater: BiFunction<Player, String, String>
    abstract val item: ItemStack

    fun getComponent(player: Player, inventory: AquaticInventory): InventoryComponent? {
        var component: InventoryComponent? = null
        var areMet = true
        for (condition in viewConditions.keys) {
            if (!condition.apply(player)) {
                val conditionComponent = viewConditions[condition]
                areMet = false
                if (conditionComponent == null) continue
                if (component != null) {
                    if (component.priority > conditionComponent.priority) continue
                }
                component = viewConditions[condition]
            }
        }
        if (areMet) {
            Bukkit.broadcastMessage("Conditions are met")
            return this
        }
        if (failItem == null) return component
        if (component == null) return failItem
        if (component.priority > failItem!!.priority) return component
        return failItem
    }

    fun onClick(event: InventoryClickEvent) {
        onClick?.accept(event)
    }


}