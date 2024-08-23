package gg.aquatic.aquaticseries.lib.interactable.impl.global.block

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable.impl.personalized.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.interactable.AudienceList
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableBreakEvent
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable.impl.personalized.block.SpawnedPacketBlockInteractable
import gg.aquatic.aquaticseries.lib.interactable.worldobject.InteractableWorldObject
import gg.aquatic.aquaticseries.lib.interactable.worldobject.PersistentInteractableWorldObject
import gg.aquatic.aquaticseries.lib.interactable.worldobject.TempInteractableWorldObject
import gg.aquatic.aquaticseries.lib.interactable.worldobject.spawned.SpawnedPersistentInteractableWorldObject
import gg.aquatic.aquaticseries.lib.interactable.worldobject.spawned.SpawnedTempInteractableWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Consumer

class BlockInteractable(
    override val id: String,
    var onInteract: Consumer<BlockInteractableInteractEvent>?,
    var onBreak: Consumer<BlockInteractableBreakEvent>?,
    override val shape: BlockShape, override val persistent: Boolean, override val worldObject: InteractableWorldObject
) : AbstractInteractable() {


    init {
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.registry[id] = this
    }

    fun onBreak(event: BlockInteractableBreakEvent) {
        onBreak?.accept(event)
    }

    fun onInteract(event: BlockInteractableInteractEvent) {
        this.onInteract?.accept(event)
    }

    override val serializer: BlockInteractableSerializer
        get() {
            return (AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.serializers[BlockInteractable::class.java] as BlockInteractableSerializer)
        }

    override fun spawn(location: Location, register: Boolean): AbstractSpawnedInteractable {
        if (worldObject is PersistentInteractableWorldObject) {
            val spawnedObject = worldObject.create(location)

            val spawned = SpawnedBlockInteractable(location, this, spawnedObject)
            spawned.spawn(null, false)

            spawnedObject.initialize(spawned)
            if (register) {
                WorldObjectHandler.registerSpawnedObject(spawnedObject)
                worldObject.serializer.save(spawnedObject.customData, spawnedObject)
            }

            return spawned
        } else if (worldObject is TempInteractableWorldObject) {
            val spawnedObject = worldObject.create(location)

            val spawned = SpawnedBlockInteractable(location, this, spawnedObject)
            spawned.spawn(null, false)

            spawnedObject.initialize(spawned)

            if (register) {
                WorldObjectHandler.registerSpawnedObject(spawnedObject)
            }
            return spawned
        }

        throw Exception("Could not initialize World Object!")
    }

    override fun spawnPacket(location: Location, register: Boolean): AbstractSpawnedPacketInteractable {
        if (worldObject is PersistentInteractableWorldObject) {
            val spawnedObject = worldObject.create(location)

            val spawned = SpawnedPacketBlockInteractable(location, this, spawnedObject)
            spawned.spawn(null, false)

            spawnedObject.initialize(spawned)

            if (register) {
                WorldObjectHandler.registerSpawnedObject(spawnedObject)
                worldObject.serializer.save(spawnedObject.customData, spawnedObject)
            }
            return spawned
        } else if (worldObject is TempInteractableWorldObject) {
            val spawnedObject = worldObject.create(location)

            val spawned = SpawnedPacketBlockInteractable(location, this, spawnedObject)
            spawned.spawn(null, false)

            spawnedObject.initialize(spawned)

            if (register) {
                WorldObjectHandler.registerSpawnedObject(spawnedObject)
            }
            return spawned
        }

        throw Exception("Could not initialize World Object!")
    }

    fun despawnOldData(data: InteractableData, location: Location) {
        processLayerCells(data.previousShape ?: return, location) { char, newLoc ->
            if (data.nullChars == null) {
                newLoc.block.type = Material.AIR
            } else if (!data.nullChars.contains(char)) {
                newLoc.block.type = Material.AIR
            }
        }
    }

    /*
    override fun spawn(location: Location, spawnedWorldObject: SpawnedWorldObject<*>): SpawnedBlockInteractable {
        val spawned = SpawnedBlockInteractable(location, this)
        spawned.spawn(null, false)

        if (spawnedWorldObject is SpawnedPersistentInteractableWorldObject) {
            spawnedWorldObject.initialize(spawned)
        } else if (spawnedWorldObject is SpawnedTempInteractableWorldObject) {
            spawnedWorldObject.initialize(spawned)
        }

        return spawned
    }

    override fun spawnPacket(
        location: Location,
        spawnedWorldObject: SpawnedWorldObject<*>
    ): AbstractSpawnedPacketInteractable {
        val spawned = SpawnedPacketBlockInteractable(location, this, spawnedWorldObject)
        spawned.spawn(null, false)

        if (worldObject is PersistentInteractableWorldObject) {
            val spawned = worldObject.create(location)
        } else if (worldObject is TempInteractableWorldObject) {

        }
        return spawned
    }
     */


    override fun onChunkLoad(data: InteractableData, location: Location) {

    }

    override fun onChunkUnload(data: InteractableData) {

    }
}