package gg.aquatic.aquaticseries.lib.action

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

class ConfiguredAction(
    val action: AbstractAction,
    val arguments: Map<String, Any?>,
) {

    fun run(player: Player, placeholders: Placeholders) {
        action.run(player, arguments, placeholders)
    }

}