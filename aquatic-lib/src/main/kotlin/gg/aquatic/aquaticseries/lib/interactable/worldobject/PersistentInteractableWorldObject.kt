package gg.aquatic.aquaticseries.lib.interactable.worldobject

import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.worldobject.spawned.SpawnedPersistentInteractableWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectSerializer
import gg.aquatic.aquaticseries.lib.worldobject.`object`.PersistentWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import org.bukkit.Chunk
import org.bukkit.Location

class PersistentInteractableWorldObject: PersistentWorldObject(), InteractableWorldObject {

    override lateinit var id: String
    override lateinit var interactable: AbstractInteractable
    override val packetBased: Boolean
        get() = TODO("Not yet implemented")

    fun initialize(interactable: AbstractInteractable) {
        this.interactable = interactable
        id = interactable.id
        register()
    }

    override val serializer: WorldObjectSerializer<PersistentInteractableWorldObject>
        get() = TODO("Not yet implemented")

    override fun onChunkLoad(chunk: Chunk) {
        TODO("Not yet implemented")
    }

    override fun onChunkUnload(chunk: Chunk) {
        TODO("Not yet implemented")
    }

    override fun onLoad(location: Location, spawned: SpawnedWorldObject<*>) {
        TODO("Not yet implemented")
    }

    override fun onUnload(location: Location, spawned: SpawnedWorldObject<*>) {
        TODO("Not yet implemented")
    }

    override fun create(location: Location): SpawnedPersistentInteractableWorldObject {
        TODO("Not yet implemented")
    }

}