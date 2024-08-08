package gg.aquatic.aquaticseries.lib.format.color.adapter

import gg.aquatic.aquaticseries.format.color.ColorAdapter
import net.kyori.adventure.text.minimessage.MiniMessage

object MiniMessageColorAdapter: ColorAdapter {
    override fun format(str: String): String {

        val component = MiniMessage.miniMessage().deserialize(str)

        TODO("Not yet implemented")
    }

    override fun format(str: List<String>): List<String> {
        TODO("Not yet implemented")
    }
}