package xyz.larkyy.aquaticfarming.data

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop
import xyz.larkyy.aquaticseries.chunk.ChunkData
import java.util.UUID

class FarmingData: ChunkData {

    val placedCrops = HashMap<UUID,AbstractPlacedCrop>()
    val cropLocations = HashMap<String,UUID>()

    fun getPlacedCrop(location: Location): AbstractPlacedCrop? {
        val str = "${location.x};${location.y};${location.z}"
        val uuid = cropLocations[str] ?: return null
        return placedCrops[uuid]
    }

    fun addPlacedCrop(placedCrop: AbstractPlacedCrop): Boolean {
        if (placedCrops.containsKey(placedCrop.uuid)) return false
        placedCrops[placedCrop.uuid] = placedCrop
        return true
    }

    fun addCropLocation(uuid: UUID, location: Location) {
        val str = "${location.x};${location.y};${location.z}"
        cropLocations[str] = uuid
    }
}