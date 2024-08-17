package gg.aquatic.aquaticseries.lib.action

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

abstract class ConfiguredAction<T>(
    val action: AbstractAction<T>,
    val arguments: Map<String, Any?>,
) {

    fun run(binder: T, placeholders: Placeholders) {
        action.run(binder, arguments, placeholders)
    }

}