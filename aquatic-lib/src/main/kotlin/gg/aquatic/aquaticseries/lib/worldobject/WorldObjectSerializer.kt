package gg.aquatic.aquaticseries.lib.worldobject

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject

abstract class WorldObjectSerializer<T: WorldObject> {

    abstract fun load(blockData: CustomBlockData): SpawnedWorldObject<T>
    abstract fun save(blockData: CustomBlockData, spawned: SpawnedWorldObject<T>)

}