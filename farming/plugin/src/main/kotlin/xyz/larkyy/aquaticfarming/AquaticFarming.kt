package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject

class AquaticFarming(injection: AbstractAquaticModuleInject) : AbstractAquaticFarming(injection) {

    init {
        instance = this
    }


}