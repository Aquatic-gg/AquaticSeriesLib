package gg.aquatic.aquaticseries.interactable.impl.block

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.toStringDetailed
import xyz.larkyy.aquaticseries.toStringSimple

class SpawnedBlockInteractable(
    override val location: Location,
    override val interactable: BlockInteractable,
) : AbstractSpawnedInteractable() {
    override val associatedLocations = ArrayList<Location>()

    override var loaded = false
    var removed = false

    fun spawn(data: InteractableData?, reset: Boolean) {
        if (removed) return
        if (data != null && !reset) {
            for (associatedLocation in associatedLocations) {
                AquaticSeriesLib.INSTANCE.interactableHandler.removeChildren(associatedLocation)
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
                AquaticSeriesLib.INSTANCE.interactableHandler.removeChildren(associatedLocation)
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

            val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
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
                PersistentDataType.STRING,
                AquaticSeriesLib.GSON.toJson(blockData)
            )
        }
        AquaticSeriesLib.INSTANCE.interactableHandler.addParent(location,this)
        for (loc in associatedLocations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.addChildren(loc,location)
        }
        loaded = true
    }

    override fun despawn() {
        removed = true
        for (associatedLocation in associatedLocations) {
            associatedLocation.block.type = Material.AIR
            AquaticSeriesLib.INSTANCE.interactableHandler.removeChildren(associatedLocation)
        }
        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        cbd.remove(AbstractInteractable.INTERACTABLE_KEY)
        cbd.clear()
        AquaticSeriesLib.INSTANCE.interactableHandler.removeParent(location)
    }
}