package gg.aquatic.aquaticseries.lib.worldobject.`object`

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.chunkcache.location.LocationObject
import org.bukkit.Location
import java.util.*

abstract class SpawnedWorldObject<T: WorldObject>: LocationObject {

    abstract val location: Location
    abstract val worldObject: T

    abstract val children: MutableList<SpawnedWorldChildrenObject<T>>

    abstract fun onLoad()
    abstract fun onUnload()

    val uuid: UUID = UUID.randomUUID()

    val customData: CustomBlockData
        get() {
            return CustomBlockData(location.block, worldObject.namespace)
        }

}