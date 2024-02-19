package xyz.larkyy.aquaticfarming.crop.placed

import org.bukkit.Location

class PlacedCropLocation(
    val location: Location,
    val placedCrop: AbstractPlacedCrop,
    val parent: Boolean
) {
}