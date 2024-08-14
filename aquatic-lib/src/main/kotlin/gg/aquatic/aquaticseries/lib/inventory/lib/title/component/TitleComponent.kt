package gg.aquatic.aquaticseries.lib.inventory.lib.title.component

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

abstract class TitleComponent {

    var length: Int = 0

    abstract fun generate(inventory: CustomInventory, player: Player): List<AquaticString>

}