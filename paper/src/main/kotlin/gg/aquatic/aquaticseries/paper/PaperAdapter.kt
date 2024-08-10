package gg.aquatic.aquaticseries.paper

import gg.aquatic.aquaticseries.lib.AquaticLibAdapter
import gg.aquatic.aquaticseries.lib.adapt.*
import gg.aquatic.aquaticseries.paper.adapt.*
import net.kyori.adventure.text.minimessage.MiniMessage
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
}