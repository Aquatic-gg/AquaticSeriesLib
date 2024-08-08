package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SpigotString(
    override val string: String
): AquaticString() {
    override fun send(player: Player) {
        player.sendMessage(formatted)
    }

    override fun broadcast() {
        Bukkit.broadcastMessage(formatted)
    }

    override fun send(vararg players: Player) {
        players.forEach { player -> send(player) }
    }

    val formatted: String
        get() {
            return ColorUtils.format(string)
        }
}