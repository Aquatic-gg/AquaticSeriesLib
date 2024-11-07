package gg.aquatic.aquaticseries.lib.util.argument

import org.bukkit.configuration.ConfigurationSection

abstract class AbstractObjectArgumentSerializer<T> {

    abstract fun load(section: ConfigurationSection, id: String): T?

}