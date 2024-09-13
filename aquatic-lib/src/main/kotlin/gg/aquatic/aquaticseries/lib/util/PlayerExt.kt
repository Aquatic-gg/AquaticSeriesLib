package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import org.bukkit.entity.Player

fun Player.addChatTabCompletion(list: List<String>) {
    AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.addTabCompletion(listOf(this),list)
}