package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import org.bukkit.event.Listener

fun Listener.register() {
    AquaticSeriesLib.INSTANCE.plugin.server.pluginManager.registerEvents(this, AquaticSeriesLib.INSTANCE.plugin)
}