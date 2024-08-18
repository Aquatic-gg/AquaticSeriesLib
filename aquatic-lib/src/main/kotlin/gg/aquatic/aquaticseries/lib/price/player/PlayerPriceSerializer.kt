package gg.aquatic.aquaticseries.lib.price.player

import gg.aquatic.aquaticseries.lib.price.ConfiguredPrice
import gg.aquatic.aquaticseries.lib.price.PriceSerializer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

object PlayerPriceSerializer {

    fun fromSection(section: ConfigurationSection): ConfiguredPrice<Player>? {
        return PriceSerializer.fromSection(section)
    }

    fun fromSections(sections: List<ConfigurationSection>): List<ConfiguredPrice<Player>> {
        return sections.mapNotNull { fromSection(it) }
    }
}