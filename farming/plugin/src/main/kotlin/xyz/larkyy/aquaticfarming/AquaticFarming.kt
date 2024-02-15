package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticfarming.crop.CropRegistryManager
import xyz.larkyy.aquaticfarming.data.AbstractDataHandler
import xyz.larkyy.aquaticfarming.data.DataHandler
import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject

class AquaticFarming(injection: AbstractAquaticModuleInject) : AbstractAquaticFarming(injection) {

    init {
        instance = this
        onEnable()
    }

    private fun onEnable() {

    }

    override val cropRegistryManager: AbstractCropRegistryManager = CropRegistryManager()
    override val dataHandler: AbstractDataHandler = DataHandler()

    override fun onDisable() {
        instance = null
    }


}