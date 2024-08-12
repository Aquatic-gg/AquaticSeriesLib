package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

abstract class AbstractAction {

    abstract fun run(player: Player, args: Map<String,Any>, placeholders: Placeholders)

    abstract fun arguments(): List<RequirementArgument>

}