package xyz.larkyy.aquaticseries.interactable

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable.Companion.INTERACTABLE_KEY

abstract class AbstractSpawnedInteractable {

    abstract val location: Location
    abstract val interactable: AbstractInteractable
    abstract val associatedLocations: List<Location>
    abstract var loaded: Boolean
    var placeCheckEnabled: Boolean = true

    companion object {
        fun get(block: Block): InteractableData? {
            val cbd = CustomBlockData(block, AquaticSeriesLib.INSTANCE.plugin)
            if (!cbd.has(INTERACTABLE_KEY, PersistentDataType.STRING)) return null
            val id = cbd.getOrDefault(INTERACTABLE_KEY, PersistentDataType.STRING, "null")
            if (id == "null") return null
            val data = AquaticSeriesLib.GSON.fromJson(id,InteractableData::class.java)
            return data
        }
    }

    val data: CustomBlockData
        get() {
            return CustomBlockData(location.block,AquaticSeriesLib.INSTANCE.plugin)
        }

    open fun despawn() {
        unload()
        for (associatedLocation in associatedLocations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.removeChildren(associatedLocation)
        }
        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        cbd.remove(INTERACTABLE_KEY)
        AquaticSeriesLib.INSTANCE.interactableHandler.removeParent(location)
        cbd.clear()
    }
    abstract fun unload()

}