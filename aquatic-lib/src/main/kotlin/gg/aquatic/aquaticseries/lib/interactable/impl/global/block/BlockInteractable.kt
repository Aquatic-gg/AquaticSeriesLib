package gg.aquatic.aquaticseries.lib.interactable.impl.global.block

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.interactable.AudienceList
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableBreakEvent
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable.impl.personalized.block.SpawnedPacketBlockInteractable
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Consumer

class BlockInteractable(
    override val id: String,
    var onInteract: Consumer<BlockInteractableInteractEvent>?,
    var onBreak: Consumer<BlockInteractableBreakEvent>?,
    override val shape: BlockShape, override val persistent: Boolean
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

    fun despawnOldData(data: InteractableData, location: Location) {
        processLayerCells(data.previousShape ?: return, location) { char, newLoc ->
            if (data.nullChars == null) {
                newLoc.block.type = Material.AIR
            }
            else if (!data.nullChars.contains(char)) {
                newLoc.block.type = Material.AIR
            }
        }
    }

    override fun spawn(location: Location): SpawnedBlockInteractable {
        val spawned = SpawnedBlockInteractable(location, this)
        spawned.spawn(null,false)
        return spawned
    }

    override fun spawn(location: Location, audienceList: AudienceList): AbstractSpawnedPacketInteractable {
        val spawned = SpawnedPacketBlockInteractable(location, this, audienceList)
        spawned.spawn(null,false)
        return spawned
    }

    override fun onChunkLoad(data: InteractableData, location: Location) {
        //serializer.deserialize(data, location, this)
        val spawned = SpawnedBlockInteractable(location, this)
        spawned.spawn(data,false)
        spawned.loaded = false
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addWorkloadJob(location.chunk) {
            spawned.spawn(data,true)
        }
    }

    override fun onChunkUnload(data: InteractableData) {

    }
}