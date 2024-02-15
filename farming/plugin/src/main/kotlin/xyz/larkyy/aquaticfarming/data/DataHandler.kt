package xyz.larkyy.aquaticfarming.data

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop
import xyz.larkyy.aquaticseries.chunk.ChunkDataContainer

class DataHandler : AbstractDataHandler() {
    override val chunkDataContainer = ChunkDataContainer(FarmingDataSerializer())

    override fun getPlacedCrop(location: Location): AbstractPlacedCrop? {
        val chunk = location.chunk
        val data = chunkDataContainer.getChunkData(chunk) ?: return null
        return data.getPlacedCrop(location)
    }

    override fun addPlacedCrop(placedCrop: AbstractPlacedCrop): Boolean {
        val chunk = placedCrop.location.chunk
        var data = chunkDataContainer.getChunkData(chunk)
        if (data == null) {
            data = FarmingData()
            chunkDataContainer.addChunkData(chunk, data)
        }
        return data.addPlacedCrop(placedCrop)
    }
}