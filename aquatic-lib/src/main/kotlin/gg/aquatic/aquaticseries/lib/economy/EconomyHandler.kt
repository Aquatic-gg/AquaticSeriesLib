package gg.aquatic.aquaticseries.lib.economy

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.util.event
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object EconomyHandler: IFeature {
    override val type: Features = Features.ECONOMY

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        event<PlayerJoinEvent> {

        }
        event<PlayerQuitEvent> {

        }
    }
}