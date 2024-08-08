package gg.aquatic.aquaticseries.interactable.impl.block

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Consumer
import xyz.larkyy.aquaticseries.*
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableBreakEvent
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableInteractEvent

class BlockInteractable(
    override val id: String,
    val onInteract: Consumer<BlockInteractableInteractEvent>,
    val onBreak: Consumer<BlockInteractableBreakEvent>,
    override val shape: BlockShape
) : AbstractInteractable() {


    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.registry[id] = this
    }

    fun onBreak(event: BlockInteractableBreakEvent) {
        onBreak.accept(event)
    }

    fun onInteract(event: BlockInteractableInteractEvent) {
        this.onInteract.accept(event)
    }

    override val serializer: BlockInteractableSerializer
        get() {
            return (AquaticSeriesLib.INSTANCE.interactableHandler.serializers[BlockInteractable::class.java] as BlockInteractableSerializer)
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
        /*
        //despawn()
        val locations = ArrayList<Location>()
        val spawned = SpawnedBlockInteractable(location, this, locations)
        Bukkit.broadcastMessage("Spawning")
        //val nullChars = ArrayList<Char>()

        processLayerCells(shape.layers, location) {char, newLoc ->
            val block = shape.blocks[char]
            if (block == null) {
                //nullChars += char
            } else {
                block.place(newLoc)
                locations += newLoc
            }
        }

        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        val blockData =
            InteractableData(id, location.yaw, location.pitch, serializer.serialize(spawned), shape.layers)
        cbd.set(INTERACTABLE_KEY, PersistentDataType.STRING, AquaticSeriesLib.GSON.toJson(blockData))

        val mainLocStr = location.toStringDetailed()

        AquaticSeriesLib.INSTANCE.interactableHandler.spawnedIntectables[mainLocStr] = spawned
        for (loc in locations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.spawnedChildren[loc.toStringSimple()] = mainLocStr
        }
        return spawned
         */
        val spawned = SpawnedBlockInteractable(location,this)
        spawned.spawn(null,false)
        return spawned
    }

    override fun onChunkLoad(data: InteractableData, location: Location) {
        //serializer.deserialize(data, location, this)
        val spawned = SpawnedBlockInteractable(location,this)
        spawned.spawn(data,false)
        spawned.loaded = false
        AquaticSeriesLib.INSTANCE.interactableHandler.addWorkloadJob(location.chunk) {
            spawned.spawn(data,true)
        }
    }

    override fun onChunkUnload(data: InteractableData) {

    }
}