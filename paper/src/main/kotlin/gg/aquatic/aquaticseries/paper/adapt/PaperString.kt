package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class PaperString(
    override var string: String
) : AquaticString() {
    override fun send(player: CommandSender) {
        val component = convert()
        player.sendMessage(component)
    }

    override fun broadcast() {
        val component = convert()
        Bukkit.broadcast(component)
    }

    override fun sendActionBar(vararg player: Player) {
        player.forEach {
            it.sendActionBar(convert())
        }
    }

    override fun setEntityName(entity: Entity) {
        entity.customName(
            convert()
        )
    }

    override fun send(vararg players: CommandSender) {
        val component = convert()
        players.forEach { player -> player.sendMessage(component) }
    }

    fun toJson(): String {
        return GsonComponentSerializer.gson().serialize(miniMessage.deserialize(string))
    }

    private val miniMessage: MiniMessage
        get() {
            return PaperAdapter.minimessage
        }

    fun convert(): Component {
        return PaperAdapter.minimessage.deserialize(ChatColor.stripColor(
            string
                .replace("&a", "<green>")
                .replace("&c", "<red>")
                .replace("&b", "<aqua>")
                .replace("&e", "<yellow>")
                .replace("&6", "<gold>")
                .replace("&d", "<light_purple>")
                .replace("&f", "<white>")
                .replace("&3", "<dark_aqua>")
                .replace("&9", "<blue>")
                .replace("&f", "<white>")
                .replace("&7", "<gray>")
                .replace("&8", "<dark_gray>")
                .replace("&4", "<dark_red>")
                .replace("&1", "<dark_blue>")
                .replace("&4", "<dark_red>")
                .replace("&8", "<dark_gray>")
                .replace("&2", "<dark_green>")
                .replace("&5", "<dark_purple>")
        ))
    }

    override fun clone(): PaperString {
        return PaperString(string)
    }
}