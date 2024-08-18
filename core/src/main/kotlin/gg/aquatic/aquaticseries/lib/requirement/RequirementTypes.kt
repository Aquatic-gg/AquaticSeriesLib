package gg.aquatic.aquaticseries.lib.requirement

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import gg.aquatic.aquaticseries.lib.requirement.player.PlayerRequirement
import gg.aquatic.aquaticseries.lib.util.AquaticObjectArgument

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
}