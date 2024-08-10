package gg.aquatic.aquaticseries.lib.action

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

class ConfiguredAction(
    val action: AbstractAction,
    args: String
) {

    private val arguments = action.readArguments(args)

    fun run(player: Player, placeholders: Placeholders) {
        action.run(player, arguments, placeholders)
    }

}