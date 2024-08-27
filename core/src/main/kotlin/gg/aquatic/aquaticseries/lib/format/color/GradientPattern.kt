package gg.aquatic.aquaticseries.lib.format.color

import org.bukkit.ChatColor
import java.awt.Color

class GradientPattern: IPattern {

    private val pattern = Regex("\\{#([A-Fa-f0-9]{6})>#([A-Fa-f0-9]{6})<}")
    override fun process(string: String): String {
        val matchResult = pattern.find(string) ?: return string
        val (startColorHex, endColorHex) = matchResult.destructured
        var str = string

        val content = pattern.replace(str, "")
        val lastColor = ChatColor.getLastColors(content)
        ColorUtils.color(
            ChatColor.stripColor(content)!!,
            Color(startColorHex.toInt(16)),
            Color(endColorHex.toInt(16)),
            lastColor
        )
        return str
    }

}