package gg.aquatic.aquaticseries.lib.util.argument

import org.bukkit.configuration.ConfigurationSection

object ArgumentSerializer {

    fun load(section: ConfigurationSection, arguments: Collection<AquaticObjectArgument<*>>): Map<String,Any?> {
        val map = HashMap<String,Any?>()
        for (argument in arguments) {
            map += argument.id to argument.load(section)
        }
        return map
    }

}