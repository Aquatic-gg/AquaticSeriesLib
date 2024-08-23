package gg.aquatic.aquaticseries.lib.worldobject

import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldChildrenObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import java.util.UUID

class ChunkRegistry {
    val parents = HashMap<String, HashMap<UUID,SpawnedWorldObject<*>>>()
    val children = HashMap<String,HashMap<UUID,SpawnedWorldChildrenObject<*>>>()
}