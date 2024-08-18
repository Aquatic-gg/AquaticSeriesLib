package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import org.bukkit.configuration.ConfigurationSection

object ActionSerializer {

    inline fun <reified T: ConfiguredAction<out Any>> fromSection(section: ConfigurationSection): T? {
        val type = section.getString("type") ?: return null
        val action = ActionTypes.actions[type]
        if (action == null) {
            println("[AquaticSeriesLib] Action type $type does not exist!")
            return null
        }

        val arguments = action.arguments()
        val args = AquaticObjectArgument.loadRequirementArguments(section, arguments)

        val configuredAction = ConfiguredAction(action, args)

        if (configuredAction !is T) {
            return null
        }
        return configuredAction
    }

    inline fun <reified T: ConfiguredAction<out Any>> fromSections(sections: List<ConfigurationSection>): List<T> {
        return sections.mapNotNull { fromSection(it) }
    }

}