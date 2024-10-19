package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

abstract class AquaticString {

    abstract var string: String

    fun replace(from: String, to: String): AquaticString {
        string = this.string.replace(from, to)
        return this
    }

    abstract fun send(player: CommandSender)
    abstract fun broadcast()
    abstract fun send(vararg players: CommandSender)

    abstract fun sendActionBar(vararg player: Player)

    abstract fun setEntityName(entity: Entity)
}