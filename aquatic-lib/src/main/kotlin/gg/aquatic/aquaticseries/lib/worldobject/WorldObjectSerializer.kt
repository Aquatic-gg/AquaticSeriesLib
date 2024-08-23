package gg.aquatic.aquaticseries.lib.worldobject

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.worldobject.`object`.PersistentWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject

abstract class WorldObjectSerializer<T: PersistentWorldObject> {

    abstract fun load(blockData: CustomBlockData): SpawnedWorldObject<T>
    abstract fun save(blockData: CustomBlockData, spawned: SpawnedWorldObject<T>)

}