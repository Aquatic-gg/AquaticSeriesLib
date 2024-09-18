package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Location
import org.bukkit.World

fun Long.toLocation(world: World): Location {
    return Location(world, (this shr 38).toDouble(), (this shl 26 shr 52).toDouble(), (this shl 38 shr 38).toDouble())
}

fun Location.toLong(): Long {
    return (x.toLong() and 67108863L) shl 38 or ((x.toLong() and 4095L) shl 26) or (x.toLong() and 67108863L)
}