package gg.aquatic.aquaticseries.lib.util.placeholder

import gg.aquatic.aquaticseries.lib.adapt.AquaticString

class Placeholder(
    val placeholder: String,
    val value: Any
) {

    fun replace(input: String): String {
        return input.replace(placeholder,value.toString())
    }

    fun replace(input: List<String>): MutableList<String> {
        val output = ArrayList<String>()
        for (s in input) {
            output += replace(s)
        }
        return output
    }
}