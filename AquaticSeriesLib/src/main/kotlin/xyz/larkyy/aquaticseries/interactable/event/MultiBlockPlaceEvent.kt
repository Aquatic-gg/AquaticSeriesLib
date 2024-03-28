package xyz.larkyy.aquaticseries.interactable.event

import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable

class MultiBlockPlaceEvent(
    val interactable: SpawnedBlockInteractable
): Event(), Cancellable {

    companion object {
        private val HANDLERS = HandlerList()

        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

    private var cancel: Boolean = false

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }

}