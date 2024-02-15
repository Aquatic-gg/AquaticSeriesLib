package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticseries.AbstractAquaticPlugin

class AquaticFarmingPlugin: AbstractAquaticPlugin() {

    override fun onLoad() {
        injections.add(AquaticFarmingInject(this,dataFolder))
    }

    override fun onEnable() {
        injectAll()
    }

    override fun onDisable() {
        ejectAll()
    }

}