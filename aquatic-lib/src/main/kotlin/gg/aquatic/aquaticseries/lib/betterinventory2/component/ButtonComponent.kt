package gg.aquatic.aquaticseries.lib.betterinventory2.component

import gg.aquatic.aquaticseries.lib.betterinventory.SlotSelection
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

class ButtonComponent(
    override val id: String,
    override val priority: Int,
    override val slotSelection: SlotSelection,
    override val viewConditions: HashMap<Function<Player, Boolean>, InventoryComponent?>,
    override val failItem: InventoryComponent?,
    override val onClick: Consumer<InventoryClickEvent>?,
    override val updateEvery: Int,
    override val textUpdater: BiFunction<Player, String, String>,
    override val item: ItemStack
) : InventoryComponent() {
}