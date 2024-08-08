package gg.aquatic.aquaticseries.interactable.impl.meg

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.Utils
import xyz.larkyy.aquaticseries.interactable.*
import xyz.larkyy.aquaticseries.interactable.event.MegInteractableInteractEvent
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockShape
import xyz.larkyy.aquaticseries.toStringDetailed
import xyz.larkyy.aquaticseries.toStringSimple
import kotlin.math.floor

class MEGInteractable(
    override val id: String,
    override val shape: BlockShape,
    val modelId: String,
    val onInteract: Consumer<MegInteractableInteractEvent>
) : AbstractInteractable() {
    override val serializer: gg.aquatic.aquaticseries.interactable.impl.meg.MegInteractableSerializer
        get() {
            return AquaticSeriesLib.INSTANCE.interactableHandler.serializers[gg.aquatic.aquaticseries.interactable.impl.meg.MEGInteractable::class.java] as gg.aquatic.aquaticseries.interactable.impl.meg.MegInteractableSerializer
        }


    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.registry[id] = this
    }

    override fun spawn(location: Location): gg.aquatic.aquaticseries.interactable.impl.meg.SpawnedMegInteractable {
        val locations = ArrayList<Location>()
        val spawned = gg.aquatic.aquaticseries.interactable.impl.meg.SpawnedMegInteractable(location, this, locations)

        Bukkit.broadcastMessage("Spawning MEG")
        val nullChars = ArrayList<Char>()

        processLayerCells(shape.layers, location) { char, newLoc ->
            val block = shape.blocks[char]
            if (block == null) {
                nullChars += char
            } else {
                block.place(newLoc)
                locations += newLoc
            }
        }
        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        val blockData =
            InteractableData(id, location.yaw, location.pitch, serializer.serialize(spawned), shape.layers, nullChars)
        cbd.set(INTERACTABLE_KEY, PersistentDataType.STRING, AquaticSeriesLib.GSON.toJson(blockData))
        AquaticSeriesLib.INSTANCE.interactableHandler.addParent(location, spawned)
        for (loc in locations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.addChildren(loc, location)
        }
        return spawned
    }

    fun onInteract(event: MegInteractableInteractEvent) {
        this.onInteract.accept(event)
    }

    override fun onChunkLoad(data: InteractableData, location: Location) {
        serializer.deserialize(data, location, this)
    }

    override fun onChunkUnload(data: InteractableData) {

    }
}