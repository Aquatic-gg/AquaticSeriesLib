package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.format.Format

fun String.toAquatic(): AquaticString {
    return AquaticSeriesLib.INSTANCE.adapter.adaptString(this)
}

fun Collection<String>.toAquatic(): List<AquaticString> {
    return map { it.toAquatic() }
}

fun String.format(): AquaticString {
    val format = AquaticSeriesLib.INSTANCE.messageFormat
    if (format == Format.LEGACY) {

    }
}