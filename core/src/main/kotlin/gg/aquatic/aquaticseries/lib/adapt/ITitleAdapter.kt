package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.entity.Player

interface ITitleAdapter {

    fun send(player: Player, title: AquaticString, subtitle: AquaticString, fadeIn: Int, stay: Int, fadeOut: Int)

}