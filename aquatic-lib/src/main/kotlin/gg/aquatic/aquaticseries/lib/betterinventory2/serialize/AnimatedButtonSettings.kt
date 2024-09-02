package gg.aquatic.aquaticseries.lib.betterinventory2.serialize

import gg.aquatic.aquaticseries.lib.betterinventory2.component.InventoryComponent
import org.bukkit.entity.Player
import java.util.TreeMap
import java.util.function.BiFunction
import java.util.function.Function

class AnimatedButtonSettings(id: String, priority: Int,
                             viewConditions: HashMap<Function<Player, Boolean>, ButtonSettings?>,
                             failItem: ButtonSettings?, onClick: ClickSettings, updateEvery: Int,
    val frames: TreeMap<Int, ButtonSettings>
) : ButtonSettings(id, priority, viewConditions, failItem, onClick, updateEvery) {
    override fun create(textUpdater: BiFunction<Player, String, String>): InventoryComponent {
        TODO("Not yet implemented")
    }
}