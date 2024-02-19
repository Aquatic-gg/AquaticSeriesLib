package xyz.larkyy.aquaticfarming.data

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop
import xyz.larkyy.aquaticseries.chunk.ChunkDataContainer
import java.util.UUID
import java.util.concurrent.CompletableFuture

class DataHandler : AbstractDataHandler() {
    override val chunkDataContainer = ChunkDataContainer(FarmingDataSerializer())

    /**
     * String - Chunk
     * String - Location
     * UUID - crop uuid
     */
    val unloadedLocations = HashMap<String,HashMap<String,UUID>>()

    override fun getPlacedCrop(location: Location): AbstractPlacedCrop? {
        val chunk = location.chunk
        val data = chunkDataContainer.getChunkData(chunk) ?: return null
        return data.getPlacedCrop(location)
    }

    override fun addPlacedCrop(placedCrop: AbstractPlacedCrop): Boolean {
        val chunk = placedCrop.baseLocation.chunk
        var data = chunkDataContainer.getChunkData(chunk)
        if (data == null) {
            data = FarmingData()
            chunkDataContainer.addChunkData(chunk, data)
        }
        if (!data.addPlacedCrop(placedCrop)) return false

        for (it in placedCrop.locations) {
            val locChunk = it.chunk
            val chunkData = chunkDataContainer.getChunkData(locChunk)
            if (chunkData == null) {
                val str = "${chunk.world.name};${chunk.x};${chunk.z}"
                val map = if (unloadedLocations.containsKey(str)) {
                    unloadedLocations[str]!!
                } else {
                    val newMap = HashMap<String,UUID>()
                    unloadedLocations[str] = newMap
                    newMap
                }
                val locStr = "${it.x};${it.y};${it.z}"
                map[locStr] = placedCrop.uuid
                continue
            }
            chunkData.addCropLocation(placedCrop.uuid,it)
        }
        return true
    }

    override fun loadChunk(chunk: String): CompletableFuture<FarmingData> {
        TODO("Not yet implemented")
    }
}