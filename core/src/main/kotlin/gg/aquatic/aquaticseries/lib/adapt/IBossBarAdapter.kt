package gg.aquatic.aquaticseries.lib.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar.Color
import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar.Style

interface IBossBarAdapter {

    fun create(
        text: AquaticString,
        color: Color,
        style: Style,
        progress: Double
    ): AquaticBossBar

}