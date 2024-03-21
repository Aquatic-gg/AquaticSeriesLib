package xyz.larkyy.aquaticseries

import org.bukkit.event.Listener

fun Listener.register() {
    AquaticSeriesLib.INSTANCE.plugin.server.pluginManager.registerEvents(this, AquaticSeriesLib.INSTANCE.plugin)
}