package gg.aquatic.aquaticseries.lib.requirement

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import gg.aquatic.aquaticseries.lib.requirement.player.PlayerInstancedRequirement
import gg.aquatic.aquaticseries.lib.requirement.player.PlayerRequirement

object RequirementTypes {

    val requirementTypes = HashMap<String, AbstractRequirement<*>>()

    fun register(id: String, requirement: AbstractRequirement<*>) {
        requirementTypes[id] = requirement
    }

    fun unregister(id: String) {
        requirementTypes -= id
    }

    operator fun get(id: String): AbstractRequirement<*>? {
        return requirementTypes[id]
    }

    fun loadPlayerRequirements(section: ConfigurationSection): ArrayList<PlayerInstancedRequirement> {
        val requirements = ArrayList<PlayerInstancedRequirement>()

        for (key in section.getKeys(false)) {
            val sec = section.getConfigurationSection(key) ?: continue

            val typeId = sec.getString("type") ?: continue
            val type = RequirementTypes[typeId] ?: continue
            if (type !is PlayerRequirement) continue

            val arguments: Map<String, Any?> = loadArguments(sec, type.arguments())
            val requirement = PlayerInstancedRequirement(type, arguments)
            requirements.add(requirement)
        }
        return requirements
    }

    private fun loadArguments(section: ConfigurationSection, arguments: List<RequirementArgument>): Map<String, Any?> {
        val args: MutableMap<String, Any?> = HashMap()

        for (arg in arguments) {
            if (section.getKeys(false).contains(arg.id)) {
                args[arg.id] = section.get(arg.id)
                continue
            } else if (arg.required) {
                Bukkit.getConsoleSender()
                    .sendMessage("§cARGUMENT §4${arg.id} §cIS MISSING, PLEASE UPDATE YOUR CONFIGURATION!")
            }
            args[arg.id] = arg.defaultValue
        }
        return args
    }

}