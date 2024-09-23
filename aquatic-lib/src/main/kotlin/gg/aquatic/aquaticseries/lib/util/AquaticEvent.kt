package gg.aquatic.aquaticseries.lib.util

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class AquaticEvent: Event() {
    companion object {
        @JvmStatic
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}