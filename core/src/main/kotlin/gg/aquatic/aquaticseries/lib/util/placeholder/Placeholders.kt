package gg.aquatic.aquaticseries.lib.util.placeholder

import gg.aquatic.aquaticseries.lib.adapt.AquaticString

class Placeholders(
) {

    val placeholders = ArrayList<Placeholder>()


    constructor(vararg placeholders: Placeholder): this() {
        this.placeholders.addAll(placeholders)
    }

    constructor(vararg placeholders: Pair<String,Any>) : this() {
        this.placeholders.addAll(placeholders.map { Placeholder(it.first, it.second) })
    }

    fun addPlaceholder(placeholder: Placeholder) {
        placeholders+=placeholders
    }

    operator fun plusAssign(placeholder: Placeholder) {
        placeholders += placeholder
    }

    fun replace(input: String): String {
        return placeholders.fold(input) { acc, placeholder ->
            placeholder.replace(acc)
        }
    }

    fun replace(input: List<String>): MutableList<String> {
        return input.map { string ->
            replace(string)
        }.toMutableList()
    }

}