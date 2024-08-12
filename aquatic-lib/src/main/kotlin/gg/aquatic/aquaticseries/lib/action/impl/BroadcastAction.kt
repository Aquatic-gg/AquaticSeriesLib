package gg.aquatic.aquaticseries.lib.action.impl

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player

class BroadcastAction: AbstractAction() {
    override fun run(player: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        val message = args["message"] as String
        message.toAquatic().broadcast()
    }

    override fun arguments(): List<RequirementArgument> {
        return listOf(
            RequirementArgument("message", "", true)
        )
    }
}