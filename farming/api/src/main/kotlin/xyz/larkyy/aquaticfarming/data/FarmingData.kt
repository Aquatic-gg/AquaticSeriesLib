package xyz.larkyy.aquaticfarming.data

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop
import xyz.larkyy.aquaticseries.chunk.ChunkData

class FarmingData: ChunkData {

    val placedCrops = HashMap<String,AbstractPlacedCrop>()

    fun getPlacedCrop(location: Location): AbstractPlacedCrop? {
        val str = "${location.x};${location.y};${location.z}"
        return placedCrops[str]
    }

    fun addPlacedCrop(placedCrop: AbstractPlacedCrop): Boolean {
        val location = placedCrop.location
        val str = "${location.x};${location.y};${location.z}"
        if (placedCrops.containsKey(str)) return false
        placedCrops[str] = placedCrop
        return true
    }


}