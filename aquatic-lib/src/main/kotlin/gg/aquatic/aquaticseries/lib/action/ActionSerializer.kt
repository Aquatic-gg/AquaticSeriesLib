package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection

object ActionSerializer {

    fun fromSection(section: ConfigurationSection): ConfiguredAction? {
        val type = section.getString("type","null") ?: return null
        val action = ActionTypes.actions[type] ?: return null
        val arguments = action.arguments()
        val args = loadRequirementArguments(section, arguments)
        return ConfiguredAction(action,args)
    }

    fun fromSections(sections: List<ConfigurationSection>): List<ConfiguredAction> {
        return sections.mapNotNull { fromSection(it) }
    }

    private fun loadRequirementArguments(
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