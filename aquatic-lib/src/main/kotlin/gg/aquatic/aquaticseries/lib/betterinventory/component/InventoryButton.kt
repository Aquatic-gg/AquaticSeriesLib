package gg.aquatic.aquaticseries.lib.betterinventory.component

import gg.aquatic.aquaticseries.lib.betterinventory.SlotSelection
import gg.aquatic.aquaticseries.lib.betterinventory.inventory.AquaticInventory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function
import kotlin.collections.HashMap

class InventoryButton(
    override val id: String,
    override val priority: Int,
    val _slotSelection: SlotSelection,
    val item: ItemStack,
    val onClick: Consumer<InventoryClickEvent>,
    val textUpdater: BiFunction<Player, String, String>,
    override val viewConditions: HashMap<Function<Player, Boolean>, InventoryComponent?>,
    override val failItem: InventoryComponent?
) : InventoryComponent() {


    override fun tick() {

    }

    override val itemStack: ItemStack?
        get() {
            return currentComponent?.itemStack
        }

    override val slotSelection: SlotSelection
        get() {
            if (currentComponent == this) {
                return _slotSelection
            }
            return currentComponent?.slotSelection ?: SlotSelection.of()
        }

}