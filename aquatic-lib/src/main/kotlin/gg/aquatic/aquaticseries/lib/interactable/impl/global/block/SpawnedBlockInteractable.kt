package gg.aquatic.aquaticseries.lib.interactable.impl.global.block

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableBreakEvent
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableInteractEvent
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Consumer
import org.bukkit.persistence.PersistentDataType as PersistentDataType1

class SpawnedBlockInteractable(
    override val location: Location,
    override val interactable: BlockInteractable,
) : AbstractSpawnedInteractable() {
    override val associatedLocations = ArrayList<Location>()

    val onInteract: Consumer<BlockInteractableInteractEvent>? = null
    val onBreak: Consumer<BlockInteractableBreakEvent>? = null

    override var loaded = false
    var removed = false

    fun spawn(data: InteractableData?, reset: Boolean) {
        val persistent = interactable.persistent
        if (removed) return
        if (data != null && !reset) {
            for (associatedLocation in associatedLocations) {
                AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
            }
            associatedLocations.clear()
            interactable.processLayerCells(data.previousShape, location) { char, newLoc ->
                associatedLocations += newLoc
            }
        } else {
            if (reset && data != null) {
                interactable.despawnOldData(data, location)
            }

            for (associatedLocation in associatedLocations) {
                AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
            }
            val nullChars = ArrayList<Char>()
            associatedLocations.clear()
            interactable.processLayerCells(interactable.shape.layers, location) { char, newLoc ->
                val block = interactable.shape.blocks[char]
                if (block == null) {
                    nullChars += char
                } else {
                    block.place(newLoc)
                    associatedLocations += newLoc
                }
            }

            if (persistent) {
                val cbd = CustomBlockData(location.block, AbstractAquaticSeriesLib.INSTANCE.plugin)
                val blockData =
                    InteractableData(
                        interactable.id,
                        location.yaw,
                        location.pitch,
                        interactable.serializer.serialize(this),
                        interactable.shape.layers,
                        nullChars
                    )
                cbd.set(
                    AbstractInteractable.INTERACTABLE_KEY,
                    PersistentDataType1.STRING,
                    AbstractAquaticSeriesLib.GSON.toJson(blockData)
                )
            }
        }
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addParent(location,this)
        for (loc in associatedLocations) {
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addChildren(loc,location)
        }
        loaded = true
    }

    override fun despawn() {
        removed = true
        for (associatedLocation in associatedLocations) {
            associatedLocation.block.type = Material.AIR
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
        }

        if (interactable.persistent) {
            val cbd = CustomBlockData(location.block, AbstractAquaticSeriesLib.INSTANCE.plugin)
            cbd.remove(AbstractInteractable.INTERACTABLE_KEY)
        }
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeParent(location)
    }
}