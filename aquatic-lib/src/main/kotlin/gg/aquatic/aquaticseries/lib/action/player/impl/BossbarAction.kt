package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar
import gg.aquatic.aquaticseries.lib.replace
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import gg.aquatic.aquaticseries.lib.util.runLaterSync
import org.bukkit.entity.Player
import java.util.function.BiFunction

class BossbarAction : AbstractAction<Player>() {
    override fun run(player: Player, args: Map<String, Any?>, textUpdater: BiFunction<Player, String, String>) {
        val message = args["message"] as String
        val progress = args["progress"] as Double
        val color = AquaticBossBar.Color.valueOf((args["color"] as String).uppercase())
        val style = AquaticBossBar.Style.valueOf((args["style"] as String).uppercase())
        val duration = args["duration"] as Int

        val bossBar = AquaticSeriesLib.INSTANCE.adapter.bossBarAdapter.create(message.toAquatic().replace(textUpdater, player), color, style, progress)
        bossBar.addPlayer(player)
        runLaterSync(duration.toLong()) {bossBar.removePlayer(player)
        }
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(
            PrimitiveObjectArgument("message", "", true),
            PrimitiveObjectArgument("progress", 0.0, false),
            PrimitiveObjectArgument("color", "BLUE", false),
            PrimitiveObjectArgument("style", "SOLID", false),
            PrimitiveObjectArgument("duration", 60, true)
        )
    }
}