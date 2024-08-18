package gg.aquatic.aquaticseries.lib.requirement.player

import gg.aquatic.aquaticseries.lib.requirement.ConfiguredRequirement
import gg.aquatic.aquaticseries.lib.requirement.RequirementSerializer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

object PlayerRequirementSerializer {

    fun fromSection(section: ConfigurationSection): ConfiguredRequirement<Player>? {
        return RequirementSerializer.fromSection(section)
    }

    fun fromSections(sections: List<ConfigurationSection>): List<ConfiguredRequirement<Player>> {
        return sections.mapNotNull { fromSection(it) }
    }


}