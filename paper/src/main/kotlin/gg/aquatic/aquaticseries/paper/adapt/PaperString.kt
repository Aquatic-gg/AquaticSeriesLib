package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class PaperString(
    override val string: String
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
        val legacyComp = LegacyComponentSerializer.legacyAmpersand().deserialize(string)
        val preparedString = LegacyComponentSerializer.legacyAmpersand().serialize(legacyComp)
        return PaperAdapter.minimessage.deserialize(preparedString)
    }
}