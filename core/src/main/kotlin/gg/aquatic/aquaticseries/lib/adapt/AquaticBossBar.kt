package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.entity.Player
import java.util.UUID

abstract class AquaticBossBar {

    abstract var text: AquaticString
    abstract var color: Color
    abstract var style: Style
    abstract var progress: Double

    val viewers = mutableListOf<UUID>()

    abstract fun addPlayer(player: Player)
    abstract fun removePlayer(player: Player)

    enum class Color {
        PINK,
        BLUE,
        RED,
        GREEN,
        YELLOW,
        PURPLE,
        WHITE
    }

    enum class Style {
        SOLID,
        SEGMENTED_6,
        SEGMENTED_10,
        SEGMENTED_12,
        SEGMENTED_20,
    }
}