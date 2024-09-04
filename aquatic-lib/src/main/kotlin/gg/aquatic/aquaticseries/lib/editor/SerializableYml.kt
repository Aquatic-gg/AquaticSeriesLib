package gg.aquatic.aquaticseries.lib.editor

import gg.aquatic.aquaticseries.lib.Config
import org.bukkit.configuration.ConfigurationSection

interface SerializableYml {

    fun save(section: ConfigurationSection)

}