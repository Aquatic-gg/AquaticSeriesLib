package gg.aquatic.aquaticseries.lib.interactable

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable.Companion.INTERACTABLE_KEY
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.persistence.PersistentDataType

abstract class AbstractSpawnedInteractable {

    abstract val location: Location
    abstract val interactable: AbstractInteractable
    abstract val associatedLocations: List<Location>
    abstract var loaded: Boolean
    abstract val spawnedWorldObject: SpawnedWorldObject<*>

    companion object {
        fun get(block: Block): InteractableData? {
            val cbd = CustomBlockData(block, AbstractAquaticSeriesLib.INSTANCE.plugin)
            if (!cbd.has(INTERACTABLE_KEY, PersistentDataType.STRING)) return null
            val id = cbd.getOrDefault(INTERACTABLE_KEY, PersistentDataType.STRING, "null")
            if (id == "null") return null
            val data = AbstractAquaticSeriesLib.GSON.fromJson(id, InteractableData::class.java)
            return data
        }
    }

    val data: CustomBlockData
        get() {
            return CustomBlockData(location.block, AbstractAquaticSeriesLib.INSTANCE.plugin)
        }

    abstract fun despawn()

}