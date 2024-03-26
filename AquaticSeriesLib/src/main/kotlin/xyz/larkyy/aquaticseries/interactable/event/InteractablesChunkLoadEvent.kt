package xyz.larkyy.aquaticseries.interactable.event

import org.bukkit.Chunk
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable

class InteractablesChunkLoadEvent(
    val chunk: Chunk,
    val interactables: List<AbstractSpawnedInteractable>
): Event() {

    companion object {
        private val HANDLERS = HandlerList()

        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}