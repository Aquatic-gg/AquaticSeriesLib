package gg.aquatic.aquaticseries.format.color

import java.util.regex.Pattern

class ClassicPattern: IPattern {

    private val pattern = Pattern.compile("(\\{|<|\\[)#([0-9A-Fa-f]{6})(}|>|\\})")

    override fun process(string: String): String {
        var str = string
        val matcher = pattern.matcher(string)
        while (matcher.find()) {
            val color = matcher.group(2)
            str = str.replace(matcher.group(), "${ColorUtils.getColor(color)}")
        }
        return str
    }
}