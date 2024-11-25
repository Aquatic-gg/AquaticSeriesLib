package gg.aquatic.aquaticseries.lib.chunkcache.location

import gg.aquatic.aquaticseries.lib.chunkcache.ChunkObject

class LocationChunkObject: ChunkObject {

    val cache = HashMap<String,MutableMap<Class<out LocationObject>,LocationObject>>()

}