package gg.aquatic.aquaticseries.lib.chunkcache.location

import gg.aquatic.aquaticseries.lib.chunkcache.ChunkCacheHandler
import org.bukkit.Chunk
import org.bukkit.Location

object LocationCacheHandler {

    private fun getCache(chunk: Chunk): LocationChunkObject? {
        return ChunkCacheHandler.getObject(chunk, LocationChunkObject::class.java) as? LocationChunkObject
    }

    fun getObjects(location: Location): MutableMap<Class<LocationObject>, LocationObject> {
        val cache = getCache(location.chunk)?.cache ?: return HashMap()
        val locId = "${location.blockX},${location.blockY},${location.blockZ}"
        return cache[locId] ?: return HashMap()
    }

    fun getObject(location: Location, clazz: Class<LocationObject>): LocationObject? {
        val objects = getObjects(location)
        return objects[clazz]
    }

    fun registerObject(obj: LocationObject, location: Location): LocationObject? {
        var cacheObj = getCache(location.chunk)
        if (cacheObj == null) {
            cacheObj = LocationChunkObject()
            ChunkCacheHandler.registerObject(cacheObj, location.chunk)
        }
        val map = cacheObj.cache.getOrPut("${location.blockX},${location.blockY},${location.blockZ}") { HashMap() }
        return map.put(obj.javaClass, obj)
    }

    fun unregisterObject(clazz: Class<LocationObject>, location: Location): LocationObject? {
        val cacheObj = getCache(location.chunk) ?: return null
        val map = cacheObj.cache["${location.blockX},${location.blockY},${location.blockZ}"] ?: return null
        return map.remove(clazz)
    }

}