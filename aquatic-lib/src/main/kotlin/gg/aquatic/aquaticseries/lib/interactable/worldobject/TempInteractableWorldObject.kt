package gg.aquatic.aquaticseries.lib.interactable.worldobject

import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.worldobject.spawned.SpawnedTempInteractableWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.TempWorldObject
import org.bukkit.Chunk
import org.bukkit.Location

class TempInteractableWorldObject : TempWorldObject(), InteractableWorldObject {
    override lateinit var id: String
    override lateinit var interactable: AbstractInteractable
    override val packetBased: Boolean
        get() = TODO("Not yet implemented")

    fun initialize(interactable: AbstractInteractable) {
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

    override fun create(location: Location): SpawnedTempInteractableWorldObject {
        val spawned = SpawnedTempInteractableWorldObject(location, this)
        interactable.spawn(location, spawned)
        return spawned
    }
}