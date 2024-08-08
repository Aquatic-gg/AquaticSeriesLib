package gg.aquatic.aquaticseries.lib.format.string.impl

import gg.aquatic.aquaticcrates.api.AbstractAudience
import gg.aquatic.aquaticseries.AquaticSeriesLib
import gg.aquatic.aquaticseries.format.string.AquaticString
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MMString(
    val component: Component
): AquaticString {
    override fun send(player: Player) {
        val audience = AquaticSeriesLib.INSTANCE.audience
        audience.player(player).sendMessage(component)

        val iS: ItemStack
    }

    override fun send(audience: AbstractAudience) {
        TODO("Not yet implemented")
    }

    override fun send(vararg players: Player) {
        TODO("Not yet implemented")
    }

    override fun broadcast() {
        TODO("Not yet implemented")
    }
}