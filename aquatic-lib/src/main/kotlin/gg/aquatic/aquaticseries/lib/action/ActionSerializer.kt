package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import org.bukkit.Bukkit
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
        val args = loadRequirementArguments(section, arguments)

        val configuredAction = ConfiguredAction(action, args)

        if (configuredAction !is T) {
            return null
        }
        return configuredAction
    }

    inline fun <reified T: ConfiguredAction<out Any>> fromSections(sections: List<ConfigurationSection>): List<T> {
        return sections.mapNotNull { fromSection(it) }
    }

    fun loadRequirementArguments(
        section: ConfigurationSection,
        arguments: List<RequirementArgument>
    ): Map<String, Any?> {
        val args: MutableMap<String, Any?> = java.util.HashMap()

        for (arg in arguments) {
            if (section.contains(arg.id)) {
                args[arg.id] = if (arg.defaultValue is List<*>) section.getList(arg.id) else section.get(arg.id)
                continue
            } else if (arg.required) {
                Bukkit.getConsoleSender()
                    .sendMessage(ColorUtils.format("&cARGUMENT &4" + arg.id + " &cIS MISSING, PLEASE UPDATE YOUR CONFIGURATION!"))
            }
            args[arg.id] = arg.defaultValue
        }
        return args
    }

}