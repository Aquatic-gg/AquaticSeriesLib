package gg.aquatic.aquaticseries.lib.betterinventory2.action

import org.bukkit.entity.Player
import java.util.function.BiFunction

class ConfiguredActionsWithConditions(
    val actions: MutableList<ConfiguredActionWithConditions>,
    val conditions: MutableList<ConfiguredConditionWithFailActions>,
    val failActions: ConfiguredActionsWithConditions?
) {

    fun tryRun(player: Player, textUpdater: BiFunction<Player, String, String>) {
        var failed = false
        for (condition in conditions) {
            if (!condition.tryCheck(player, textUpdater)) {
                failed = true
                break
            }
        }
        if (failed) {
            failActions?.tryRun(player, textUpdater)
            return
        }
        for (action in actions) {
            action.tryRun(player, textUpdater)
        }
    }

}