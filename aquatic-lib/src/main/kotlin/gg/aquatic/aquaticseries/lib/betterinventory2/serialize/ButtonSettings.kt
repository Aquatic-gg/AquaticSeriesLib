package gg.aquatic.aquaticseries.lib.betterinventory2.serialize

import gg.aquatic.aquaticseries.lib.betterinventory2.component.InventoryComponent
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

abstract class ButtonSettings(
    val id: String,
    var priority: Int,
    var viewConditions: HashMap<Function<Player, Boolean>, ButtonSettings?>,
    var failItem: ButtonSettings?,
    var onClick: ClickSettings,
    var updateEvery: Int,
) {

    abstract fun create(textUpdater: BiFunction<Player, String, String>): InventoryComponent
}