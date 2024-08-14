package gg.aquatic.aquaticseries.lib.inventory.lib.title

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory
import gg.aquatic.aquaticseries.lib.inventory.lib.title.component.TitleComponent

class TitleHolder {

    val components = ArrayList<TitleComponent>()

    fun generate(inventory: CustomInventory, player: Player): AquaticString {
        val titleComponents = components.flatMap { it.generate(inventory, player) }
        return titleComponents.joinToString { it.string }.toAquatic()
    }

    companion object {
        fun of(vararg components: TitleComponent): TitleHolder {
            val titleHolder = TitleHolder()
            titleHolder.components.addAll(components)
            return titleHolder
        }
    }

}