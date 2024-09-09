package gg.aquatic.aquaticseries.lib.accessory.type.back

import gg.aquatic.aquaticseries.lib.accessory.Accessory
import gg.aquatic.aquaticseries.lib.accessory.AppliedAccessory
import org.bukkit.entity.Player

class BackAccessory(
    override val id: String, override val namespace: String, visual: BackVisual
) : Accessory {

    override fun apply(player: Player): AppliedAccessory {
        return AppliedAccessory(this)
    }

    override fun unapply(player: Player, appliedAccessory: AppliedAccessory) {

    }

}