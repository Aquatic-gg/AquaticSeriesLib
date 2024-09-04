package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.replace
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import java.util.function.BiFunction

class MessageAction : AbstractAction<Player>() {

    override fun run(player: Player, args: Map<String, Any?>, textUpdater: BiFunction<Player, String, String>) {
        val messages = if (args["message"] != null) listOf(args["message"] as String) else args["messages"] as List<String>

        for (message in messages) {
            message.toAquatic().replace(textUpdater, player).send(player)
        }
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(
            PrimitiveObjectArgument("message", "", false),
            PrimitiveObjectArgument("messages", ArrayList<String>(), false)
        )
    }


}