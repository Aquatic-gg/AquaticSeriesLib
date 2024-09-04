package gg.aquatic.aquaticseries.lib.action

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import java.util.function.BiFunction

open class ConfiguredAction<T>(
    val action: AbstractAction<T>,
    val arguments: Map<String, Any?>,
) {

    fun run(binder: T, textUpdater: BiFunction<T, String, String>) {
        action.run(binder, arguments, textUpdater)
    }

}