package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticfarming.harvestable.HarvestableManager
import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject

abstract class AbstractAquaticFarming(
    val injection: AbstractAquaticModuleInject
) {

    abstract val cropRegistryManager: AbstractCropRegistryManager
    abstract val harvestableManager: HarvestableManager

    companion object {
        var instance: AbstractAquaticFarming? = null
    }

    abstract fun onDisable()
}