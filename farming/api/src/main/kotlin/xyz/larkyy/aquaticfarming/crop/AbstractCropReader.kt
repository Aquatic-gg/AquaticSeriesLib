package xyz.larkyy.aquaticfarming.crop

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop

abstract class AbstractCropReader {

    abstract fun getCrop(location: Location): AbstractPlacedCrop?

}