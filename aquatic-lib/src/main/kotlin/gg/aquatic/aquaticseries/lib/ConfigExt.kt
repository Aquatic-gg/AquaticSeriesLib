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
    val list = mutableListOf<ConfigurationSection>()
    if (!this.isList(path)) return list

    val objectList = this.getList(path) ?: return list

    for (obj in objectList) {
        val section = when (obj) {
            is Map<*, *> -> createConfigurationSectionFromMap(obj)
            else -> null
        }
        section?.let { list.add(it) }
    }
    return list
}

private fun createConfigurationSectionFromMap(map: Map<*, *>): ConfigurationSection {
    val mc = MemoryConfiguration()
    for ((key, value) in map) {
        when (value) {
            is Map<*, *> -> {
                mc.createSection(key.toString(), createConfigurationSectionFromMap(value).getValues(false))
            }
            is List<*> -> {
                mc.createSection(key.toString(), handleList(value))
            }
            else -> {
                mc[key.toString()] = value
            }
        }
    }
    return mc
}

private fun handleList(list: List<*>): Map<*,*> {
    val resultMap = mutableMapOf<String, Any>()
    list.forEachIndexed { index, item ->
        when (item) {
            is Map<*, *> -> resultMap[index.toString()] = createConfigurationSectionFromMap(item)
            else -> {
                if (item != null) resultMap[index.toString()] = item
            }
        }
    }
    return resultMap
}