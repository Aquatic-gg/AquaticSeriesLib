package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PaperString(
    override val string: String
): AquaticString() {
    override fun send(player: CommandSender) {
        val component = miniMessage.deserialize(string)
        player.sendMessage(component)
    }

    override fun broadcast() {
        val component = miniMessage.deserialize(string)
        Bukkit.broadcast(component)
    }

    override fun sendActionBar(vararg player: Player) {
        player.forEach {
            it.sendActionBar(miniMessage.deserialize(string))
        }
    }

    override fun send(vararg players: CommandSender) {
        val component = miniMessage.deserialize(string)
        players.forEach { player -> player.sendMessage(component) }
    }

    private val miniMessage: MiniMessage
        get() {
            return PaperAdapter.minimessage
        }
}