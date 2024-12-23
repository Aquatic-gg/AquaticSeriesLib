package gg.aquatic.aquaticseries.lib.editortest

import gg.aquatic.aquaticseries.lib.editor.SerializableYml
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import gg.aquatic.aquaticseries.lib.editor.type.Chat
import gg.aquatic.aquaticseries.lib.editor.type.MultiChoice
import gg.aquatic.aquaticseries.lib.editortest.visual.BlockVisual
import gg.aquatic.aquaticseries.lib.editortest.visual.MEGVisual
import gg.aquatic.aquaticseries.lib.util.Config
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

class Crate(val config: Config) : SerializableYml {

    companion object {
        val visualEdit = MultiChoice(listOf(
            MultiChoice.Choice("ModelEngine", ItemStack(Material.IRON_HORSE_ARMOR)) { MEGVisual() },
            MultiChoice.Choice("Block", ItemStack(Material.CHEST)) { BlockVisual() }
        ))
        val idEdit = Chat("ID", ItemStack(Material.NAME_TAG))
    }

    val visual = Editable(MEGVisual(),visualEdit)
    val testId = Editable("example", idEdit)
    //val actions = MapEdit<String,String>(HashMap())

    override fun save(section: ConfigurationSection) {
        visual.value.save(section.createSection("visual"))
    }
}