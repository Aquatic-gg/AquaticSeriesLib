package gg.aquatic.aquaticseries.lib

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import org.bukkit.configuration.file.FileConfiguration
import java.util.*

fun FileConfiguration.getSectionList(path: String): List<ConfigurationSection> {
    if (!this.isList(path)) return ArrayList<ConfigurationSection>()

    val list: MutableList<ConfigurationSection> = LinkedList<ConfigurationSection>()

    for (obj in this.getList(path) ?: return list) {
        if (obj is Map<*, *>) {
            val mc = MemoryConfiguration()
            mc.addDefaults(obj as Map<String, Any>)

            list.add(mc)
        }
    }
    return list
}

fun ConfigurationSection.getSectionList(path: String): List<ConfigurationSection> {
    if (!this.isList(path)) return ArrayList<ConfigurationSection>()

    val list: MutableList<ConfigurationSection> = LinkedList<ConfigurationSection>()

    for (obj in this.getList(path) ?: return list) {
        if (obj is Map<*, *>) {
            val mc = MemoryConfiguration()
            mc.addDefaults(obj as Map<String, Any>)

            list.add(mc)
        }
    }
    return list
}