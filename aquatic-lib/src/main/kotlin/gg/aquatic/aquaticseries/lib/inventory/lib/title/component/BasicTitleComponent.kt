package gg.aquatic.aquaticseries.lib.inventory.lib.title.component

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class BasicTitleComponent(
    val component: AquaticString
): TitleComponent() {
    constructor(string: String): this(string.toAquatic())

    override fun generate(inventory: CustomInventory, player: Player): List<AquaticString> {
        return listOf(component)
    }
}