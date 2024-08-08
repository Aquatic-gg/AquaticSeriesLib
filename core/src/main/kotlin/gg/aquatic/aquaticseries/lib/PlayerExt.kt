package gg.aquatic.aquaticseries.lib

import net.kyori.adventure.audience.Audience
import org.bukkit.entity.Player

fun Player.toAudience(): Audience {
    return AquaticSeriesLib.INSTANCE.audience.player(this)
}