package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.updatePAPIPlaceholders
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.function.BiFunction

class CommandAction: AbstractAction<Player>() {
    override fun run(player: Player, args: Map<String, Any?>, textUpdater: BiFunction<Player, String, String>) {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            textUpdater.apply(player, args["command"]!!.toString().updatePAPIPlaceholders(player))
        )
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(PrimitiveObjectArgument("command", "", true))
    }
}