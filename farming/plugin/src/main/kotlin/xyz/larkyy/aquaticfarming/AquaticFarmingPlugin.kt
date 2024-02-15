package xyz.larkyy.aquaticfarming

import org.bukkit.plugin.java.JavaPlugin

class AquaticFarmingPlugin: JavaPlugin() {

    override fun onLoad() {
        AquaticFarmingInject(this,dataFolder).inject()
    }

    override fun onEnable() {

    }

}