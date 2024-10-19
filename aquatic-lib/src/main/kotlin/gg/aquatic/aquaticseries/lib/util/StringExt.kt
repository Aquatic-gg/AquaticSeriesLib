package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import gg.aquatic.aquaticseries.paper.adapt.PaperString
import gg.aquatic.aquaticseries.spigot.adapt.SpigotString
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import java.util.function.BiFunction

fun String.toAquatic(): AquaticString {
    return AquaticSeriesLib.INSTANCE.adapter.adaptString(this)
}

fun Collection<String>.toAquatic(): List<AquaticString> {
    return map { it.toAquatic() }
}

fun PaperString.replace(placeholders: Placeholders): PaperString {
    val replaced = placeholders.replace(this.string)
    return PaperString(replaced)
}

fun SpigotString.replace(placeholders: Placeholders): SpigotString {
    val replaced = placeholders.replace(this.string)
    return SpigotString(replaced)
}

fun AquaticString.replace(placeholders: Placeholders): AquaticString {
    val replaced = placeholders.replace(this.string)
    return replaced.toAquatic()
}

fun AquaticString.replace(textUpdater: BiFunction<Player, String, String>, player: Player): AquaticString {
    return textUpdater.apply(player, string).toAquatic()
}

fun Placeholders.replace(input: AquaticString): AquaticString {
    return input.replace(this)
}

fun String.updatePAPIPlaceholders(player: Player): String {
    AquaticSeriesLib.INSTANCE.plugin.server.pluginManager.getPlugin("PlaceholderAPI") ?: return this
    return PlaceholderAPI.setPlaceholders(player, this)
}

fun AquaticString.updatePAPIPlaceholders(player: Player): AquaticString {
    return this.string.updatePAPIPlaceholders(player).toAquatic()
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
        fadeOut = fadeOut
    )
}