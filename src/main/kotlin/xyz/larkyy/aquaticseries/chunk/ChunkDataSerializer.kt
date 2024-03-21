package xyz.larkyy.aquaticseries.chunk

abstract class ChunkDataSerializer<T: ChunkData> {

    abstract fun serialize(value: MutableList<T>): String
    abstract fun deserialize(value: String): MutableList<T>

}