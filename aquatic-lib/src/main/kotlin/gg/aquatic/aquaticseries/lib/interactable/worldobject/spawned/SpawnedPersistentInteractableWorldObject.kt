package gg.aquatic.aquaticseries.lib.interactable.worldobject.spawned

import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable.worldobject.PersistentInteractableWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldChildrenObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import org.bukkit.Location

class SpawnedPersistentInteractableWorldObject(
    override val location: Location,
    override val worldObject: PersistentInteractableWorldObject
) : SpawnedWorldObject<PersistentInteractableWorldObject>() {

    lateinit var interactable: AbstractSpawnedInteractable

    override val children: List<SpawnedWorldChildrenObject<PersistentInteractableWorldObject>> = mutableListOf()

    fun initialize(interactable: AbstractSpawnedInteractable) {
        this.interactable = interactable
    }

    override fun onLoad() {

    }

    override fun onUnload() {
        interactable.despawn()
    }
}