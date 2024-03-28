package xyz.larkyy.aquaticseries.interactable.impl.entity

import org.bukkit.Location
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.interactable.impl.meg.MEGInteractable
import xyz.larkyy.aquaticseries.toLocation
import xyz.larkyy.aquaticseries.toStringDetailed

class EntityInteractableSerializer: AbstractInteractableSerializer<SpawnedEntityInteractable>() {
    override fun serialize(value: SpawnedEntityInteractable): String {
        return value.location.toStringDetailed()
    }

    override fun deserialize(
        value: InteractableData,
        location: Location,
        interactable: AbstractInteractable
    ): SpawnedEntityInteractable? {
        if (interactable !is EntityInteractable) return null
        val realLoc = value.data.toLocation() ?: return null
        return interactable.spawn(realLoc)
    }
}