package gg.aquatic.aquaticseries.lib.action.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player

class TitleAction : AbstractAction() {
    override fun run(player: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        val title = args["title"] as String
        val subtitle = args["subtitle"] as String
        val fadeIn = args["fadeIn"] as Int
        val stay = args["stay"] as Int
        val fadeOut = args["fadeOut"] as Int

        AbstractAquaticSeriesLib.INSTANCE.adapter.titleAdapter.send(
            player,
            title.toAquatic(),
            subtitle.toAquatic(),
            fadeIn,
            stay,
            fadeOut
        )
    }

    override fun arguments(): List<RequirementArgument> {
        return listOf(
            RequirementArgument("title", "", true),
            RequirementArgument("subtitle", "", true),
            RequirementArgument("fadeIn", 0, true),
            RequirementArgument("stay", 60, true),
            RequirementArgument("fadeOut", 0, true)
        )
    }
}