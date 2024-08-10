package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.*
import org.bukkit.plugin.java.JavaPlugin

abstract class AquaticLibAdapter {

    abstract val plugin: JavaPlugin

    abstract val itemStackAdapter: IItemStackAdapter
    abstract val inventoryAdapter: IInventoryAdapter
    abstract val titleAdapter: ITitleAdapter
    abstract val bossBarAdapter: IBossBarAdapter

    abstract fun adaptString(string: String): AquaticString

}