package gg.aquatic.aquaticseries.lib.format.color

import net.md_5.bungee.api.ChatColor
import java.awt.Color
import kotlin.math.abs
import kotlin.math.pow

class ColorUtils {

    companion object {

        private val colors = HashMap<Color, ChatColor>()
        private val patterns = listOf(ClassicPattern(), GradientPattern())

        fun format(str: List<String>): MutableList<String> {
            return str.map { format(it) }.toMutableList()
        }

        fun format(str: String): String {
            return process(str)
        }

        private fun getClosestColor(color: Color): ChatColor {
            var nearestColor: Color? = null
            var nearestDistance = 2.147483647E9

            for (constantColor in colors.keys) {
                val distance = (color.red - constantColor.red).toDouble().pow(2.0) +
                        (color.green - constantColor.green).toDouble().pow(2.0) +
                        (color.blue - constantColor.blue).toDouble().pow(2.0)
                if (nearestDistance > distance) {
                    nearestColor = constantColor
                    nearestDistance = distance
                }
            }
            return colors[nearestColor]!!
        }

        fun process(string: String): String {
            var str = ChatColor.translateAlternateColorCodes('&',string)
            patterns.forEach {
                str = it.process(str)
            }
            return str
        }

        fun color(string: String, start: Color, end: Color, color: String): String {
            val stringBuilder = StringBuilder()
            val colors = createGradient(start,end,string.length)
            val characters = string.split("")

            for (i in string.indices) {
                stringBuilder.append(colors[i])
                    .append(color)
                    .append(characters[i])
            }
            return stringBuilder.toString()
        }

        fun getColor(string: String): ChatColor {
            return ChatColor.of(Color(string.toInt(16)))
        }

        private fun createGradient(start: Color, end: Color, step: Int): List<ChatColor> {
            val stepR = abs(start.red - end.red) / (step - 1)
            val stepG = abs(start.green - end.green) / (step - 1)
            val stepB = abs(start.blue - end.blue) / (step - 1)

            val direction = arrayOf(
                if (start.red < end.red) 1 else -1,
                if (start.green < end.green) 1 else -1,
                if (start.blue < end.blue) 1 else -1
            )

            val list = ArrayList<ChatColor>()
            for (i in 0..<step) {
                val color = Color(
                    start.red + stepR * i * direction[0],
                    start.green + stepG * i * direction[1],
                    start.blue + stepB * i * direction[2],
                )
                list.add(ChatColor.of(color))
            }
            return list
        }
    }

}