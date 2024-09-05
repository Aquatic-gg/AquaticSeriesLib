package gg.aquatic.aquaticseries.lib.betterinventory2.serialize

import gg.aquatic.aquaticseries.lib.betterinventory2.SlotSelection
import gg.aquatic.aquaticseries.lib.betterinventory2.component.ButtonComponent
import gg.aquatic.aquaticseries.lib.betterinventory2.component.InventoryComponent
import gg.aquatic.aquaticseries.lib.item.CustomItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

class StaticButtonSettings(
    id: String, priority: Int,
    viewConditions: HashMap<Function<Player, Boolean>, ButtonSettings?>,
    failItem: ButtonSettings?, onClick: ClickSettings, updateEvery: Int,
    val item: CustomItem,
    val slotSelection: SlotSelection
) : ButtonSettings(id, priority, viewConditions, failItem, onClick, updateEvery) {

    override fun create(textUpdater: BiFunction<Player, String, String>, callback: Consumer<InventoryClickEvent>): InventoryComponent {
        val mappedConditions = HashMap(viewConditions.mapValues { it.value?.create(textUpdater, callback) })
        return ButtonComponent(
            id,
            priority,
            slotSelection,
            mappedConditions,
            failItem?.create(textUpdater, callback),
            { e ->
                onClick.handleClick(e)
                callback.accept(e)
            },
            updateEvery,
            textUpdater,
            item.getItem()
        )
    }
}