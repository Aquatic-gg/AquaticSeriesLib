package gg.aquatic.aquaticseries.lib.worldobject.`object`

import org.bukkit.Location
import java.util.*

abstract class SpawnedWorldChildrenObject<T: WorldObject> {

    abstract val parent: SpawnedWorldObject<T>
    abstract val location: Location

    val uuid: UUID = UUID.randomUUID()
}