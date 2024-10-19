package gg.aquatic.aquaticseries.lib.editor

import org.bukkit.configuration.ConfigurationSection

interface SerializableYml {

    fun save(section: ConfigurationSection)

}