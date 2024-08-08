package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.entity.Player

fun String.toAquatic(): AquaticString {
    return AquaticSeriesLib.INSTANCE.adapter.adaptString(this)
}

fun Collection<String>.toAquatic(): List<AquaticString> {
    return map { it.toAquatic() }
}

fun AquaticString.sendTitle(
    player: Player,
    title: AquaticString,
    subtitle: AquaticString,
    fadeIn: Int,
    stay: Int,
    fadeOut: Int
) {
    AquaticSeriesLib.INSTANCE.adapter.titleAdapter.send(
        player = player,
        title = title,
        subtitle = subtitle,
        fadeIn = fadeIn,
        stay = stay,
        fadeOut = fadeOut)
}