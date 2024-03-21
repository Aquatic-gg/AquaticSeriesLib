package xyz.larkyy.aquaticseries.chunk

class DyingChunkData<T: ChunkData>(
    val chunkData: T,
    val dieAfter: Int
) {

    var ticksAlive = 0

    fun tick(): Boolean {
        ticksAlive++
        return (ticksAlive >= dieAfter)
    }

}