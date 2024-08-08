package gg.aquatic.aquaticseries.interactable.impl.meg

import org.bukkit.Location
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.toLocation
import xyz.larkyy.aquaticseries.toStringDetailed

class MegInteractableSerializer: AbstractInteractableSerializer<SpawnedMegInteractable>() {
    override fun serialize(value: SpawnedMegInteractable): String {
        return value.location.toStringDetailed()
    }

    override fun deserialize(
        value: InteractableData,
        location: Location,
        interactable: AbstractInteractable
    ): SpawnedMegInteractable? {
        if (interactable !is gg.aquatic.aquaticseries.interactable.impl.meg.MEGInteractable) return null
        val realLoc = value.data.toLocation() ?: return null
        return interactable.spawn(realLoc)
    }
}