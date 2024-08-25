package gg.aquatic.aquaticseries.lib.interactable2.base

import gg.aquatic.aquaticseries.lib.interactable2.AbstractInteractable
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.TempWorldObject
import org.bukkit.Chunk
import org.bukkit.Location

class TempInteractableBase: InteractableBase, TempWorldObject() {
    override lateinit var id: String
    lateinit var interactable: AbstractInteractable<*>

    internal fun initialize(interactable: AbstractInteractable<*>) {
        this.interactable = interactable
        id = interactable.id
        register()
    }

    override fun onChunkLoad(chunk: Chunk) {
    }

    override fun onChunkUnload(chunk: Chunk) {
    }

    override fun onLoad(location: Location, spawned: SpawnedWorldObject<*>) {
    }

    override fun onUnload(location: Location, spawned: SpawnedWorldObject<*>) {
    }

    override fun create(location: Location): SpawnedWorldObject<*> {
        return SpawnedInteractableBase(location, this)
    }
}