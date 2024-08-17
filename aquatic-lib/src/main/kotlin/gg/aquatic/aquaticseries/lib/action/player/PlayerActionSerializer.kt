package gg.aquatic.aquaticseries.lib.action.player

import gg.aquatic.aquaticseries.lib.action.ActionTypes
import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection

object PlayerActionSerializer {

    fun fromSection(section: ConfigurationSection): PlayerConfiguredAction? {
        val type = section.getString("type") ?: return null
        val action = ActionTypes.actions[type]
        if (action == null) {
            println("[AquaticSeriesLib] Action type $type does not exist!")
            return null
        }
        if (action !is AbstractPlayerAction) {
            return null
        }
        val arguments = action.arguments()
        val args = loadRequirementArguments(section, arguments)
        return PlayerConfiguredAction(action, args)
    }

    fun fromSections(sections: List<ConfigurationSection>): List<PlayerConfiguredAction> {
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