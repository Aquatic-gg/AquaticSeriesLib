package gg.aquatic.aquaticseries.lib.betterinventory2.action

import gg.aquatic.aquaticseries.lib.requirement.ConfiguredRequirement
import org.bukkit.entity.Player
import java.util.function.BiFunction

class ConfiguredConditionWithFailActions(
    val condition: ConfiguredRequirement<Player>,
    val failActions: ConfiguredActionsWithConditions?
) {

    fun tryCheck(player: Player, textUpdater: BiFunction<Player, String, String>): Boolean {
        if (!condition.check(player)) {
            failActions?.tryRun(player, textUpdater)
            return false
        }
        return true
    }
}