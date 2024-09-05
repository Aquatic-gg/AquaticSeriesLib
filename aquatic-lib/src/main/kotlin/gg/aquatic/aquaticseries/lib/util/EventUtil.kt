package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import java.util.function.Consumer

inline fun <reified T : Event> event(
    ignoredCancelled: Boolean = false,
    priority: EventPriority = EventPriority.NORMAL,
    callback: Consumer<T>
): Listener {
    val listener = object : Listener {}
    Bukkit.getPluginManager().registerEvent(
        T::class.java,
        listener,
        priority,
        { _, event -> callback.accept(event as T) },
        AbstractAquaticSeriesLib.INSTANCE.plugin,
        ignoredCancelled
    )
    return listener
}