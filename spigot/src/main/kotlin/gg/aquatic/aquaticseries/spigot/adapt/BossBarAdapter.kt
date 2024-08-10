package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IBossBarAdapter

object BossBarAdapter : IBossBarAdapter {
    override fun create(
        text: AquaticString,
        color: AquaticBossBar.Color,
        style: AquaticBossBar.Style,
        progress: Double
    ): AquaticBossBar {
        return SpigotBossBar(text, color, style, progress)
    }
}