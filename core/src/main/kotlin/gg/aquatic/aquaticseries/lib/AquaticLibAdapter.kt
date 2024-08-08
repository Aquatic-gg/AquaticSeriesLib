package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IInventoryAdapter
import gg.aquatic.aquaticseries.lib.adapt.IItemStackAdapter
import org.bukkit.plugin.java.JavaPlugin

abstract class AquaticLibAdapter {

    abstract val plugin: JavaPlugin

    abstract val itemStackAdapter: IItemStackAdapter
    abstract val inventoryAdapter: IInventoryAdapter

    abstract fun adaptString(string: String): AquaticString

}