package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString

fun String.toAquatic(): AquaticString {
    return AquaticSeriesLib.INSTANCE.adapter.adaptString(this)
}

fun Collection<String>.toAquatic(): List<AquaticString> {
    return map { it.toAquatic() }
}