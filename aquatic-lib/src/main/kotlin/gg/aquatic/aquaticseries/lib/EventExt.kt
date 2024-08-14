package gg.aquatic.aquaticseries.lib

import org.bukkit.Bukkit
import org.bukkit.event.Event

fun Event.call() {
    Bukkit.getServer().pluginManager.callEvent(this)
}