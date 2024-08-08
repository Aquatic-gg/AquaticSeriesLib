package gg.aquatic.aquaticseries.lib.chunk

class DyingChunkData<T: gg.aquatic.aquaticseries.lib.chunk.ChunkData>(
    val chunkData: T,
    val dieAfter: Int
) {

    var ticksAlive = 0

    fun tick(): Boolean {
        ticksAlive++
        return (ticksAlive >= dieAfter)
    }

}