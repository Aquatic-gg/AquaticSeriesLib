package gg.aquatic.aquaticseries.lib.interactable

import org.bukkit.Location

abstract class AbstractInteractableSerializer<T : AbstractSpawnedInteractable> {

    abstract fun serialize(value: T): String

    abstract fun deserialize(value: InteractableData, location: Location, interactable: AbstractInteractable): T?

}