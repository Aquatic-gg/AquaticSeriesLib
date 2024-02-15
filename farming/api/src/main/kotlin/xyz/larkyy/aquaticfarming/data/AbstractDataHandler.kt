package xyz.larkyy.aquaticfarming.data

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop
import xyz.larkyy.aquaticseries.chunk.ChunkDataContainer

abstract class AbstractDataHandler {

    abstract val chunkDataContainer: ChunkDataContainer<FarmingData>

    abstract fun getPlacedCrop(location: Location): AbstractPlacedCrop?

    abstract fun addPlacedCrop(placedCrop: AbstractPlacedCrop): Boolean

}