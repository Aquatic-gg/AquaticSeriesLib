package gg.aquatic.aquaticseries

import org.bukkit.Bukkit
import org.bukkit.Location

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