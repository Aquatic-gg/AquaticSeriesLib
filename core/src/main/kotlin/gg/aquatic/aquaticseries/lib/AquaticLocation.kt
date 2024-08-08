package gg.aquatic.aquaticseries.lib

import org.bukkit.Bukkit
import org.bukkit.Location

class AquaticLocation(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float
) {

    fun toLocation(): Location? {
        val world = Bukkit.getWorld(world) ?: return null
        return Location(world, x, y, z, yaw, pitch)
    }

}

fun Location.toAquaticLocation(): AquaticLocation {
    return AquaticLocation(
        world!!.name,
        x,
        y,
        z,
        yaw,
        pitch
    )
}

fun String.toAquaticLocation(): AquaticLocation {
    val parts = this.split(";")
    if (parts.size == 4) {
        return AquaticLocation(
            parts[0],
            parts[1].toDouble(),
            parts[2].toDouble(),
            parts[3].toDouble(),
            0f,
            0f
        )
    }
    return AquaticLocation(
        parts[0],
        parts[1].toDouble(),
        parts[2].toDouble(),
        parts[3].toDouble(),
        parts[4].toFloat(),
        parts[5].toFloat()
    )
}