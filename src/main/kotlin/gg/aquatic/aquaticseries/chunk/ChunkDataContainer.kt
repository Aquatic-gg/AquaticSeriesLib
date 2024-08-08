package gg.aquatic.aquaticseries.chunk

import org.bukkit.Chunk

class ChunkDataContainer<T: ChunkData>(
    val serializer: ChunkDataSerializer<T>
) {

    val active = HashMap<String,T>()
    val inactive = HashMap<String,DyingChunkData<T>>()

    fun getChunkData(chunk: Chunk): T? {
        val str = "${chunk.world.name};${chunk.x};${chunk.z}"
        return active[str]
    }

    fun addChunkData(chunk: Chunk, value: T) {
        val str = "${chunk.world.name};${chunk.x};${chunk.z}"
        active[str] = value
    }
}