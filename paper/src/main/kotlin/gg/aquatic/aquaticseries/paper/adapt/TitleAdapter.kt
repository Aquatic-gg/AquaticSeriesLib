package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.ITitleAdapter
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.title.Title
import org.bukkit.entity.Player
import java.time.Duration

object TitleAdapter : ITitleAdapter {
    override fun send(
        player: Player,
        title: AquaticString,
        subtitle: AquaticString,
        fadeIn: Int,
        stay: Int,
        fadeOut: Int
    ) {
        player.showTitle(
            Title.title(
                PaperAdapter.minimessage.deserialize(title.string),
                PaperAdapter.minimessage.deserialize(subtitle.string),
                Title.Times.times(
                    Duration.ofMillis(fadeIn.toLong() * 50L),
                    Duration.ofMillis(stay.toLong() * 50L),
                    Duration.ofMillis(fadeOut.toLong() * 50L)
                )
            )
        )
    }
}