package gg.aquatic.aquaticseries.lib.action.player.impl

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.action.player.AbstractPlayerAction
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

class MessageAction : AbstractPlayerAction() {

    override fun run(player: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        val messages = if (args["message"] != null) listOf(args["message"] as String) else args["messages"] as List<String>

        for (message in messages) {
            placeholders.replace(message).toAquatic().send(player)
        }
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(
            PrimitiveObjectArgument("message", "", false),
            PrimitiveObjectArgument("messages", ArrayList<String>(), false)
        )
    }


}