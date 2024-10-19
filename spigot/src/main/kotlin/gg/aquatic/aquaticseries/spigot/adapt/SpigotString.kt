package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class SpigotString(
    override var string: String
): AquaticString() {
    override fun send(player: CommandSender) {
        player.sendMessage(formatted)
    }

    override fun broadcast() {
        Bukkit.broadcastMessage(formatted)
    }

    override fun sendActionBar(vararg player: Player) {
        for (player1 in player) {
            player1.spigot().sendMessage(ChatMessageType.ACTION_BAR, *net.md_5.bungee.api.chat.TextComponent.fromLegacyText(formatted))
        }
    }

    override fun setEntityName(entity: Entity) {
        entity.customName = formatted
    }

    override fun send(vararg players: CommandSender) {
        players.forEach { player -> send(player) }
    }

    val formatted: String
        get() {
            return ColorUtils.format(string)
        }
}