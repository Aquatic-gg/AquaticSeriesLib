package gg.aquatic.aquaticseries.lib.interactable.impl.meg

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
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
            return AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.serializers[MEGInteractable::class.java] as MegInteractableSerializer
        }


    init {
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.registry[id] = this
    }

    override fun spawn(location: Location, persistent: Boolean): SpawnedMegInteractable {
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
        if (persistent) {
            val cbd = CustomBlockData(location.block, AbstractAquaticSeriesLib.INSTANCE.plugin)
            val blockData =
                InteractableData(id, location.yaw, location.pitch, serializer.serialize(spawned), shape.layers, nullChars)
            cbd.set(INTERACTABLE_KEY, PersistentDataType.STRING, AbstractAquaticSeriesLib.GSON.toJson(blockData))
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addParent(location, spawned)
        }

        for (loc in locations) {
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addChildren(loc, location)
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