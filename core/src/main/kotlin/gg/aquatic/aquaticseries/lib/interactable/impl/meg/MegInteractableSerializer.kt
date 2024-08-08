package gg.aquatic.aquaticseries.lib.interactable.impl.meg

import org.bukkit.Location
import gg.aquatic.aquaticseries.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.interactable.AbstractInteractableSerializer
import gg.aquatic.aquaticseries.interactable.InteractableData
import gg.aquatic.aquaticseries.toLocation
import gg.aquatic.aquaticseries.toStringDetailed

class MegInteractableSerializer: AbstractInteractableSerializer<SpawnedMegInteractable>() {
    override fun serialize(value: SpawnedMegInteractable): String {
        return value.location.toStringDetailed()
    }

    override fun deserialize(
        value: InteractableData,
        location: Location,
        interactable: AbstractInteractable
    ): SpawnedMegInteractable? {
        if (interactable !is MEGInteractable) return null
        val realLoc = value.data.toLocation() ?: return null
        return interactable.spawn(realLoc)
    }
}