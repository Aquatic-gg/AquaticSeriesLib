package gg.aquatic.aquaticseries.lib.interactable2.base

import gg.aquatic.aquaticseries.lib.interactable2.SpawnedInteractable
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldChildrenObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.Location

class SpawnedInteractableBase<T: WorldObject>(override val location: Location, override val worldObject: T) : SpawnedWorldObject<T>() {

    val appliedInteractables = HashMap<String,SpawnedInteractable<*>>()
    override val children: MutableList<SpawnedWorldChildrenObject<T>> = mutableListOf()

    override fun onLoad() {

    }

    override fun onUnload() {

    }
}