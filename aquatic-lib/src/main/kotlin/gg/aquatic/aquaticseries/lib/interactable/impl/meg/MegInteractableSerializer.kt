package gg.aquatic.aquaticseries.lib.interactable.impl.meg

import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractableSerializer
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import gg.aquatic.aquaticseries.lib.util.toLocation
import gg.aquatic.aquaticseries.lib.util.toStringDetailed
import org.bukkit.Location

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