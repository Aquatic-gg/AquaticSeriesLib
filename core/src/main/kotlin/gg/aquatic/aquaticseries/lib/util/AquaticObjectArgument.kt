package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection

class AquaticObjectArgument(
    val id: String, val defaultValue: Any?, val required: Boolean
) {

    companion object {
        fun loadRequirementArguments(
            section: ConfigurationSection,
            arguments: List<AquaticObjectArgument>
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

}