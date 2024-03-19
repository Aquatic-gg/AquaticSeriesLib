package xyz.larkyy.aquaticseries.interactable

import org.bukkit.Location

abstract class AbstractInteractableSerializer<T : AbstractSpawnedInteractable> {

    abstract fun serialize(value: T): String

    abstract fun deserialize(value: String, location: Location, interactable: AbstractInteractable): T?

}