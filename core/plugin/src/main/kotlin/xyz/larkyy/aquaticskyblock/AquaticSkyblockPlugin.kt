package xyz.larkyy.aquaticskyblock

import xyz.larkyy.aquaticfarming.AquaticFarmingInject
import java.io.File

class AquaticSkyblockPlugin: AbstractAquaticSkyblockPlugin() {

    override fun onLoad() {
    }

    override fun onEnable() {
        initializeExtensions()
    }

    override fun onDisable() {

    }

    fun initializeExtensions() {
        dataFolder.mkdirs()
        val extensions = mutableListOf(Extensions.FARMING)
        val folders = HashMap<Extensions,File>()

        extensions.forEach {
            val dataFolder = File(dataFolder,it.id)
            dataFolder.mkdirs()
        }

        // FARMING
        val folder = folders[Extensions.FARMING]!!
        AquaticFarmingInject(this,folder)
    }

    enum class Extensions(val id: String) {
        FARMING("farming")
    }

}