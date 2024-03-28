package xyz.larkyy.aquaticseries.chunk

import org.bukkit.Chunk

class ChunkDataManager {

    val registries = HashMap<String,MutableMap<String,HashMap<String,Any>>>()

    fun getRegistry(chunk: Chunk): HashMap<String,Any>? {
        val world = registries[chunk.world.name] ?: return null
        val registry = world["${chunk.x};${chunk.z}"]
        return registry
    }

    fun getRegistry(worldName: String, x: Int, z: Int): HashMap<String,Any>? {
        val world = registries[worldName] ?: return null
        val registry = world["${x};${z}"]
        return registry
    }

    fun getOrCreateRegistry(chunk: Chunk): HashMap<String,Any> {
        val world = registries.getOrPut(chunk.world.name) { HashMap() }
        val registry = world.getOrPut("${chunk.x};${chunk.z}") { HashMap() }
        return registry
    }

    fun removeRegistry(chunk: Chunk) {
        val world = registries[chunk.world.name] ?: return
        world.remove("${chunk.x};${chunk.z}")
    }


}