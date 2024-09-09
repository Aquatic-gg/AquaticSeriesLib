package gg.aquatic.aquaticseries.lib.accessory

import org.bukkit.entity.Player

interface Accessory {

    val id: String
    val namespace: String

    fun apply(player: Player): AppliedAccessory

    fun unapply(player: Player, appliedAccessory: AppliedAccessory)

}