package gg.aquatic.aquaticseries.lib.interactable2

import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldChildrenObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.Location

class SpawnedInteractableBaseChildren<T: WorldObject>(
    override val parent: SpawnedWorldObject<T>,
    override val location: Location
) : SpawnedWorldChildrenObject<T>() {
}