package gg.aquatic.aquaticseries.spigot

import gg.aquatic.aquaticseries.lib.adapt.AquaticLibAdapter
import gg.aquatic.aquaticseries.lib.adapt.*
import gg.aquatic.aquaticseries.spigot.adapt.*
import org.bukkit.entity.Entity
import org.bukkit.entity.TextDisplay
import org.bukkit.plugin.java.JavaPlugin

class SpigotAdapter(override val plugin: JavaPlugin) : AquaticLibAdapter() {
    override val itemStackAdapter: IItemStackAdapter = ItemStackAdapter
    override val inventoryAdapter: IInventoryAdapter = InventoryAdapter
    override val titleAdapter: ITitleAdapter = TitleAdapter
    override val bossBarAdapter: IBossBarAdapter = BossBarAdapter

    override fun adaptString(string: String): AquaticString {
        return SpigotString(string)
    }

    override fun getEntityName(entity: Entity): AquaticString {
        return SpigotString(entity.name)
    }

    override fun setDisplayText(entity: Entity, text: AquaticString) {
        if (entity !is TextDisplay) return
        entity.text = text.string
    }

    override fun getDisplayText(entity: Entity): AquaticString? {
        if (entity !is TextDisplay) return null
        val text = entity.text ?: return null
        return SpigotString(text)
    }
}