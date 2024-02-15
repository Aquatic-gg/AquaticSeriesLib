package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticfarming.data.AbstractDataHandler
import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject

abstract class AbstractAquaticFarming(
    val injection: AbstractAquaticModuleInject
) {

    abstract val cropRegistryManager: AbstractCropRegistryManager
    abstract val dataHandler: AbstractDataHandler

    companion object {
        var instance: AbstractAquaticFarming? = null
    }

    abstract fun onDisable()
}