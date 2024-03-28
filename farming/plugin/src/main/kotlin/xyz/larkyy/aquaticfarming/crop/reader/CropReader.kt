package xyz.larkyy.aquaticfarming.crop.reader

import org.bukkit.Location
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticfarming.crop.AbstractCropReader
import xyz.larkyy.aquaticfarming.crop.placed.AbstractPlacedCrop

class CropReader: AbstractCropReader() {

    override fun getCrop(location: Location): AbstractPlacedCrop? {
        //val dataHandler = AbstractAquaticFarming.instance?.dataHandler ?:
        return null
        //return dataHandler.getPlacedCrop(location)
    }

}