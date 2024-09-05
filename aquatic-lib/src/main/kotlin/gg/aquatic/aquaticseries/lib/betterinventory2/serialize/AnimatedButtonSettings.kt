package gg.aquatic.aquaticseries.lib.betterinventory2.serialize

import gg.aquatic.aquaticseries.lib.betterinventory2.component.AnimatedButtonComponent
import gg.aquatic.aquaticseries.lib.betterinventory2.component.InventoryComponent
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.TreeMap
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

class AnimatedButtonSettings(id: String, priority: Int,
                             viewConditions: HashMap<Function<Player, Boolean>, ButtonSettings?>,
                             failItem: ButtonSettings?, onClick: ClickSettings, updateEvery: Int,
    val frames: TreeMap<Int, ButtonSettings>
) : ButtonSettings(id, priority, viewConditions, failItem, onClick, updateEvery) {
    override fun create(textUpdater: BiFunction<Player, String, String>, callback: Consumer<InventoryClickEvent>): InventoryComponent {
        val mappedConditions = HashMap(viewConditions.mapValues { it.value?.create(textUpdater, callback) })
        val generatedFrames = TreeMap(frames.mapValues { it.value.create(textUpdater, callback) })
        return AnimatedButtonComponent(
            id,
            priority,
            mappedConditions,
            failItem?.create(textUpdater, callback),
            { e ->
                onClick.handleClick(e)
                callback.accept(e)
            },
            updateEvery,
            textUpdater,
            generatedFrames
        )
    }
}