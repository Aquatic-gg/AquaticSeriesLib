package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.action.player.AbstractPlayerAction
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player

class TitleAction : AbstractPlayerAction() {
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

    override fun arguments(): List<AquaticObjectArgument> {
        return listOf(
            AquaticObjectArgument("title", "", true),
            AquaticObjectArgument("subtitle", "", true),
            AquaticObjectArgument("fadeIn", 0, true),
            AquaticObjectArgument("stay", 60, true),
            AquaticObjectArgument("fadeOut", 0, true)
        )
    }
}