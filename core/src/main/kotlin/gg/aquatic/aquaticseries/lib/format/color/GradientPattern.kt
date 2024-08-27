package gg.aquatic.aquaticseries.lib.format.color

import org.bukkit.ChatColor
import java.awt.Color

class GradientPattern: IPattern {

    private val pattern = Regex("\\{#([A-Fa-f0-9]{6})>#([A-Fa-f0-9]{6})<}(.*?)((?=\\{|$))")
    override fun process(string: String): String {
        val matches = pattern.findAll(string)
        if (matches.none()) {
            return string
        }
        var processedText = ""
        var lastIndex = 0
        for (match in matches) {
            processedText += string.substring(lastIndex until match.range.first)
            val (startColorHex, endColorHex, innerText) = match.destructured
            val lastColor = ChatColor.getLastColors(innerText)
            processedText += ColorUtils.color(
                ChatColor.stripColor(innerText)!!,
                Color(startColorHex.toInt(16)),
                Color(endColorHex.toInt(16)),
                lastColor
            )
            lastIndex = match.range.last + 1
        }
        return processedText
    }

}