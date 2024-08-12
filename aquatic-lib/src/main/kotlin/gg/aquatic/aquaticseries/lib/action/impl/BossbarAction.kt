package gg.aquatic.aquaticseries.lib.action.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class BossbarAction : AbstractAction() {
    override fun run(player: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        val message = args["message"] as String
        val progress = args["progress"] as Double
        val color = AquaticBossBar.Color.valueOf((args["color"] as String).uppercase())
        val style = AquaticBossBar.Style.valueOf((args["style"] as String).uppercase())
        val duration = args["duration"] as Int

        val bossBar = AbstractAquaticSeriesLib.INSTANCE.adapter.bossBarAdapter.create(message.toAquatic(), color, style, progress)
        bossBar.addPlayer(player)
        object : BukkitRunnable() {
            override fun run() {
                bossBar.removePlayer(player)
            }
        }.runTaskLater(AbstractAquaticSeriesLib.INSTANCE.plugin,duration.toLong())
    }

    override fun arguments(): List<RequirementArgument> {
        return listOf(
            RequirementArgument("message", "", true),
            RequirementArgument("progress", 0.0, false),
            RequirementArgument("color", "BLUE", false),
            RequirementArgument("style", "SOLID", false),
            RequirementArgument("duration", 60, true)
        )
    }
}