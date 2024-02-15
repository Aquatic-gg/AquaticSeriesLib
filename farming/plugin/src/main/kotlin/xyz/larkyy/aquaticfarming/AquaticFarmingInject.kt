package xyz.larkyy.aquaticfarming

import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject
import java.io.File

class AquaticFarmingInject(plugin: JavaPlugin, dataFolder: File) : AbstractAquaticModuleInject(plugin, dataFolder) {
    override fun inject() {
        AquaticFarming(this)
    }

    override fun eject() {
        AbstractAquaticFarming.instance?.onDisable()
    }
}