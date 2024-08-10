package gg.aquatic.aquaticseries.lib.action

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

abstract class AbstractAction {

    abstract fun run(player: Player, args: Map<String,Any>, placeholders: Placeholders)

    abstract fun readArguments(string: String): Map<String,Any>

}