package xyz.larkyy.aquaticseries.chunk

import org.bukkit.Chunk

class ChunkDataContainer<T: ChunkData>(
    val serializer: ChunkDataSerializer<T>
) {

    val data = HashMap<String,T>()

    fun getChunkData(chunk: Chunk): T? {
        val str = "${chunk.world.name};${chunk.x};${chunk.z}"
        return data[str]
    }

    fun addChunkData(chunk: Chunk, value: T) {
        val str = "${chunk.world.name};${chunk.x};${chunk.z}"
        data[str] = value
    }
}