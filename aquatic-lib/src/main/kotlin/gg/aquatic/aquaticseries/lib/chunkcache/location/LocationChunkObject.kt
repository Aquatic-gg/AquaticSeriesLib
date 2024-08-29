package gg.aquatic.aquaticseries.lib.chunkcache.location

import gg.aquatic.aquaticseries.lib.chunkcache.ChunkObject
import java.util.UUID

class LocationChunkObject: ChunkObject {

    val cache = HashMap<String,MutableMap<Class<LocationObject>,LocationObject>>()

}