package gg.aquatic.aquaticseries.lib.network.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ServerNetworkConnectEvent(
    val serverId: String
): Event() {
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