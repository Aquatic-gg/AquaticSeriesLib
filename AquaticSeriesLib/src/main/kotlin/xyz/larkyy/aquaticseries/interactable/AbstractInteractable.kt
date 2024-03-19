package xyz.larkyy.aquaticseries.interactable

import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Consumer

abstract class AbstractInteractable {

    abstract val id: String
    abstract val onInteract: Consumer<PlayerInteractEvent>
    abstract val serializer: AbstractInteractableSerializer<*>

    abstract fun spawn(location: Location): AbstractSpawnedInteractable

    open fun onInteract(event: PlayerInteractEvent) {
        onInteract.accept(event)
    }
}