package gg.aquatic.aquaticseries.lib.interactable.impl.meg

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import gg.aquatic.aquaticseries.lib.interactable.event.MegInteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable.impl.block.BlockShape
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Consumer

class MEGInteractable(
    override val id: String,
    override val shape: BlockShape,
    val modelId: String,
    val onInteract: Consumer<MegInteractableInteractEvent>
) : AbstractInteractable() {
    override val serializer: MegInteractableSerializer
        get() {
            return AquaticSeriesLib.INSTANCE.interactableHandler!!.serializers[MEGInteractable::class.java] as gg.aquatic.aquaticseries.lib.interactable.impl.meg.MegInteractableSerializer
        }


    init {
        AquaticSeriesLib.INSTANCE.interactableHandler!!.registry[id] = this
    }

    override fun spawn(location: Location): SpawnedMegInteractable {
        val locations = ArrayList<Location>()
        val spawned =
            SpawnedMegInteractable(location, this, locations)

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
        AquaticSeriesLib.INSTANCE.interactableHandler!!.addParent(location, spawned)
        for (loc in locations) {
            AquaticSeriesLib.INSTANCE.interactableHandler!!.addChildren(loc, location)
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