package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Bukkit
import org.bukkit.event.Event

fun Event.call() {
    Bukkit.getServer().pluginManager.callEvent(this)
}