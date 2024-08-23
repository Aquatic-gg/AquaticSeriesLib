package gg.aquatic.aquaticseries.lib.interactable.worldobject.spawned

import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable.worldobject.TempInteractableWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldChildrenObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import org.bukkit.Location

class SpawnedTempInteractableWorldObject(override val location: Location,
                                         override val worldObject: TempInteractableWorldObject
) : SpawnedWorldObject<TempInteractableWorldObject>() {

    lateinit var interactable: AbstractSpawnedInteractable

    override val children: List<SpawnedWorldChildrenObject<TempInteractableWorldObject>> = mutableListOf()

    fun initialize(interactable: AbstractSpawnedInteractable) {
        this.interactable = interactable
    }

    override fun onLoad() {

    }

    override fun onUnload() {

    }
}