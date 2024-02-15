package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject

abstract class AbstractAquaticFarming(
    injection: AbstractAquaticModuleInject
) {

    companion object {
        private var _instance: AbstractAquaticFarming? = null
        var instance: AbstractAquaticFarming? = null
            get() {
                val inst = _instance
                if (inst == null) throw Exception("Plugin was not initialized!")
                else return inst
            }
    }

}