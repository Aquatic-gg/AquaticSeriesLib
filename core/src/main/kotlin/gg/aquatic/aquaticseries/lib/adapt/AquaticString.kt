package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.entity.Player

abstract class AquaticString {

    abstract val string: String

    fun replace(from: String, to: String): AquaticString {
        this.string.replace(from, to)
        return this
    }

    abstract fun send(player: Player)
    abstract fun broadcast()
    abstract fun send(vararg players: Player)

    abstract fun sendActionBar(vararg player: Player)
}