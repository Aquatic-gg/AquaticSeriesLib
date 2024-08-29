package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin

abstract class AquaticLibAdapter {

    abstract val plugin: JavaPlugin

    abstract val itemStackAdapter: IItemStackAdapter
    abstract val inventoryAdapter: IInventoryAdapter
    abstract val titleAdapter: ITitleAdapter
    abstract val bossBarAdapter: IBossBarAdapter

    abstract fun adaptString(string: String): AquaticString
    abstract fun getEntityName(entity: Entity): AquaticString
    abstract fun setDisplayText(entity: Entity, text: AquaticString)
    abstract fun getDisplayText(entity: Entity): AquaticString?

}