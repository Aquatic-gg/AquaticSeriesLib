package gg.aquatic.aquaticseries.lib

import org.bukkit.event.Listener

fun Listener.register() {
    AbstractAquaticSeriesLib.INSTANCE.plugin.server.pluginManager.registerEvents(this, AbstractAquaticSeriesLib.INSTANCE.plugin)
}