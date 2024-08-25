package gg.aquatic.aquaticseries.lib.interactable2

import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import org.bukkit.Location

interface SpawnedInteractable<T: AbstractInteractable<*>> {

    val location: Location
    val base: T
    val spawnedInteractableBase: SpawnedInteractableBase<*>

    val associatedLocations: Collection<Location>

    fun despawn()

    fun onInteract(event: InteractableInteractEvent)
}