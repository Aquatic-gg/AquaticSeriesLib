package gg.aquatic.aquaticseries.lib.action.player

import gg.aquatic.aquaticseries.lib.action.ActionSerializer
import gg.aquatic.aquaticseries.lib.action.ConfiguredAction
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

object PlayerActionSerializer {

    fun fromSection(section: ConfigurationSection): ConfiguredAction<Player>? {
        return ActionSerializer.fromSection(section)
    }

    fun fromSections(sections: List<ConfigurationSection>): List<ConfiguredAction<Player>> {
        return sections.mapNotNull { fromSection(it) }
    }

}