package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.replace
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.updatePAPIPlaceholders
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import org.bukkit.entity.Player
import java.util.function.BiFunction

class ActionbarAction: AbstractAction<Player>() {
    override fun run(player: Player, args: Map<String, Any?>, textUpdater: BiFunction<Player, String, String>) {
        val message = (args["message"] as String).updatePAPIPlaceholders(player)
        message.toAquatic().replace(textUpdater, player).sendActionBar(player)
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(PrimitiveObjectArgument("message", "", true))
    }
}