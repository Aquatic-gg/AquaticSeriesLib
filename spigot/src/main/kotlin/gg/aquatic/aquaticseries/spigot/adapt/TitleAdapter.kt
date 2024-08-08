package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.ITitleAdapter
import org.bukkit.entity.Player

object TitleAdapter : ITitleAdapter {
    override fun send(
        player: Player,
        title: AquaticString,
        subtitle: AquaticString,
        fadeIn: Int,
        stay: Int,
        fadeOut: Int
    ) {
        player.sendTitle(
            if (title is SpigotString) title.formatted else title.string,
            if (title is SpigotString) title.formatted else title.string,
            fadeIn,
            stay,
            fadeOut
        )
    }
}