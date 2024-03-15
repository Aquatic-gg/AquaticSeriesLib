package xyz.larkyy.aquaticseries.interactable

import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Consumer

abstract class AbstractInteractable {

    abstract val location: Location
    abstract val onInteract: Consumer<PlayerInteractEvent>

    abstract fun spawn()
    abstract fun despawn()

    open fun onInteract(event: PlayerInteractEvent) {
        onInteract.accept(event)
    }
}