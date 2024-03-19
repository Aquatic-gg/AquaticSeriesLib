package xyz.larkyy.aquaticseries.interactable

import org.bukkit.Location

abstract class AbstractSpawnedInteractable {

    abstract val location: Location
    abstract val interactable: AbstractInteractable
    abstract val associatedLocations: List<Location>

    abstract fun despawn()

}