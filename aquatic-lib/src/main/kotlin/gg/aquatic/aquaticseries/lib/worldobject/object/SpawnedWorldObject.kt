package gg.aquatic.aquaticseries.lib.worldobject.`object`

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Location
import java.util.*

abstract class SpawnedWorldObject<T: WorldObject> {

    abstract val location: Location
    abstract val worldObject: T

    abstract val children: List<SpawnedWorldChildrenObject<T>>

    abstract fun onLoad()
    abstract fun onUnload()

    val uuid: UUID = UUID.randomUUID()

    val customData: CustomBlockData
        get() {
            return CustomBlockData(location.block, worldObject.namespace)
        }

}