package gg.aquatic.aquaticseries.lib.action.impl

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

class CommandAction: AbstractAction() {
    override fun run(player: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            placeholders.replace(args["command"]!!.toString())
        )
    }

    override fun arguments(): List<RequirementArgument> {
        return listOf(RequirementArgument("command", "", true))
    }
}