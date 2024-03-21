package xyz.larkyy.aquaticseries.interactable.impl.block

import com.jeff_media.customblockdata.CustomBlockData
import io.th0rgal.oraxen.recipes.builders.ShapedBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Structure
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.*
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableBreakEvent
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableInteractEvent
import kotlin.collections.ArrayList
import kotlin.math.floor

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
        processLayerCells(data.previousShape ?: return, location) { _, newLoc ->
            newLoc.block.type = Material.AIR
        }
    }

    override fun spawn(location: Location): SpawnedBlockInteractable {
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
    }

    override fun onChunkLoad(data: InteractableData, location: Location) {
        despawnOldData(data, location)
        serializer.deserialize(data, location, this)
    }

    override fun onChunkUnload(data: InteractableData) {

    }
}