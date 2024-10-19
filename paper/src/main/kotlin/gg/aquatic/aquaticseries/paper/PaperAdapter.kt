package gg.aquatic.aquaticseries.paper

import gg.aquatic.aquaticseries.lib.adapt.*
import gg.aquatic.aquaticseries.paper.adapt.*
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Entity
import org.bukkit.entity.TextDisplay
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class PaperAdapter(override val plugin: JavaPlugin) : AquaticLibAdapter() {

    companion object {
        val minimessage = MiniMessage.miniMessage()
        lateinit var adapter: PaperAdapter
    }

    override val itemStackAdapter: IItemStackAdapter = ItemStackAdapter
    override val inventoryAdapter: IInventoryAdapter = InventoryAdapter
    override val titleAdapter: ITitleAdapter = TitleAdapter
    override val bossBarAdapter: IBossBarAdapter = BossBarAdapter

    init {
        adapter = this
    }

    override fun adaptString(string: String): AquaticString {
        return PaperString(string)
    }

    override fun getEntityName(entity: Entity): AquaticString {
        return PaperString( minimessage.serialize(entity.customName() ?: return PaperString("")))
    }

    override fun setDisplayText(entity: Entity, text: AquaticString) {
        if (entity !is TextDisplay) return
        entity.text(minimessage.deserialize(text.string))
    }

    override fun getDisplayText(entity: Entity): AquaticString? {
        if (entity !is TextDisplay) return null
        return PaperString(minimessage.serialize(entity.customName() ?: return null))
    }

    fun showItem(itemStack: ItemStack): HoverEvent<HoverEvent.ShowItem> {
        return itemStack.asHoverEvent()
    }
}