package gg.aquatic.aquaticseries.lib.requirement

import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.util.AquaticObjectArgument
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection

object RequirementSerializer {

    inline fun <reified T: ConfiguredRequirement<out Any>> fromSection(section: ConfigurationSection): T? {
        val type = section.getString("type") ?: return null
        val requirement = RequirementTypes.requirementTypes[type]
        if (requirement == null) {
            println("[AquaticSeriesLib] Action type $type does not exist!")
            return null
        }

        val arguments = requirement.arguments()
        val args = AquaticObjectArgument.loadRequirementArguments(section, arguments)

        val configuredRequirement = ConfiguredRequirement(requirement, args)

        if (configuredRequirement !is T) {
            return null
        }
        return configuredRequirement
    }

    inline fun <reified T: ConfiguredRequirement<out Any>> fromSections(sections: List<ConfigurationSection>): List<T> {
        return sections.mapNotNull { fromSection(it) }
    }

}