package gg.aquatic.aquaticseries.lib.worldobject.`object`

import gg.aquatic.aquaticseries.lib.chunkcache.location.LocationObject
import org.bukkit.Location
import java.util.*

abstract class SpawnedWorldChildrenObject<T: WorldObject>: LocationObject {

    abstract val parent: SpawnedWorldObject<T>
    abstract val location: Location

    val uuid: UUID = UUID.randomUUID()
}