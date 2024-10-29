package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Bukkit
import org.bukkit.Location
import kotlin.math.atan2
import kotlin.math.sqrt

fun Location.toStringSimple(): String {
    return "${this.world!!.name};${this.x};${this.y};${this.z}"
}

fun Location.toStringDetailed(): String {
    return "${this.world!!.name};${this.x};${this.y};${this.z};${this.yaw};${this.pitch}"
}

fun String.toLocation(): Location? {
    val parts = this.split(";")
    if (parts.size < 4) return null
    val world = Bukkit.getWorld(parts[0]) ?: return null
    val x = parts[1].toDoubleOrNull() ?: return null
    val y = parts[2].toDoubleOrNull() ?: return null
    val z = parts[3].toDoubleOrNull() ?: return null

    if (parts.size > 5) {
        val yaw = parts[4].toFloatOrNull() ?: return null
        val pitch = parts[5].toFloatOrNull() ?: return null
        return Location(world, x, y, z, yaw, pitch)
    }
    return Location(world, x, y, z)
}

fun Location.lookAtYawPitch(to: Location): Pair<Float, Float> {
    val dx = to.x - this.x
    val dy = to.y - this.y
    val dz = to.z - this.z

    val distanceXZ = sqrt(dx * dx + dz * dz) // Distance on the XZ plane

    val yaw = Math.toDegrees(atan2(-dx, dz)).toFloat() // Yaw calculation
    val pitch = Math.toDegrees(atan2(-dy, distanceXZ)).toFloat() // Pitch calculation

    return Pair(yaw, pitch)
}