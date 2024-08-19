package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.action.player.AbstractPlayerAction
import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar
import gg.aquatic.aquaticseries.lib.replace
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class BossbarAction : AbstractPlayerAction() {
    override fun run(player: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        val message = args["message"] as String
        val progress = args["progress"] as Double
        val color = AquaticBossBar.Color.valueOf((args["color"] as String).uppercase())
        val style = AquaticBossBar.Style.valueOf((args["style"] as String).uppercase())
        val duration = args["duration"] as Int

        val bossBar = AbstractAquaticSeriesLib.INSTANCE.adapter.bossBarAdapter.create(message.toAquatic().replace(placeholders), color, style, progress)
        bossBar.addPlayer(player)
        object : BukkitRunnable() {
            override fun run() {
                bossBar.removePlayer(player)
            }
        }.runTaskLater(AbstractAquaticSeriesLib.INSTANCE.plugin,duration.toLong())
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