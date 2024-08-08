package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SpigotString(
    override val string: String
): AquaticString() {
    override fun send(player: Player) {
        player.sendMessage(string)
    }

    override fun broadcast() {
        Bukkit.broadcastMessage(string)
    }

    override fun send(vararg players: Player) {
        players.forEach { player -> send(player) }
    }
}