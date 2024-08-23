package gg.aquatic.aquaticseries.lib.worldobject.`object`

import org.bukkit.Location

abstract class SpawnedWorldChildrenObject<T: WorldObject> {

    abstract val parent: SpawnedWorldObject<T>
    abstract val location: Location
}