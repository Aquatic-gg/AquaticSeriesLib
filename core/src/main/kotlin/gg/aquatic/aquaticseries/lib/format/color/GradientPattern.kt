package gg.aquatic.aquaticseries.lib.format.color

import org.bukkit.ChatColor
import java.awt.Color
import java.util.regex.Pattern

class GradientPattern: IPattern {

    private val pattern = Pattern.compile("(\\{|<|\\[)#([0-9A-Fa-f]{6})(}|>|\\})")
    override fun process(string: String): String {
        var str = string
        val matcher = pattern.matcher(string)
        while (matcher.find()) {
            val start = matcher.group(2)
            val end = matcher.group(6)
            val content = matcher.group(4)
            val lastColor = ChatColor.getLastColors(content)
            str = str.replace(
                matcher.group(),
                ColorUtils.color(
                    ChatColor.stripColor(content)!!,
                    Color(start.toInt(16)),
                    Color(end.toInt(16)),
                    lastColor
                )
            )
        }
        return str
    }


}