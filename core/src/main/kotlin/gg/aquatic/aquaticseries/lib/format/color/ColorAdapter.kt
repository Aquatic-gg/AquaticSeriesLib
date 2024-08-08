package gg.aquatic.aquaticseries.lib.format.color

interface ColorAdapter {

    fun format(str: String): String
    fun format(str: List<String>): List<String>

}