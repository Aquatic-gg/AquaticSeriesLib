package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.replace
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.updatePAPIPlaceholders
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import org.bukkit.entity.Player
import java.util.function.BiFunction

class TitleAction : AbstractAction<Player>() {
    override fun run(player: Player, args: Map<String, Any?>, textUpdater: BiFunction<Player, String, String>) {
        val title = (args["title"] as String).updatePAPIPlaceholders(player)
        val subtitle = (args["subtitle"] as String).updatePAPIPlaceholders(player)
        val fadeIn = args["fadeIn"] as Int
        val stay = args["stay"] as Int
        val fadeOut = args["fadeOut"] as Int

        AquaticSeriesLib.INSTANCE.adapter.titleAdapter.send(
            player,
            title.toAquatic().replace(textUpdater, player),
            subtitle.toAquatic().replace(textUpdater, player),
            fadeIn,
            stay,
            fadeOut
        )
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(
            PrimitiveObjectArgument("title", "", true),
            PrimitiveObjectArgument("subtitle", "", true),
            PrimitiveObjectArgument("fadeIn", 0, true),
            PrimitiveObjectArgument("stay", 60, true),
            PrimitiveObjectArgument("fadeOut", 0, true)
        )
    }
}