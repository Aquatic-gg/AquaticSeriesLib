package gg.aquatic.aquaticseries.lib.util.argument

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.configuration.ConfigurationSection

object ArgumentSerializer {

    suspend fun load(section: ConfigurationSection, arguments: Collection<AquaticObjectArgument<*>>): Map<String,Any?> = withContext(Dispatchers.IO) {
        val map = HashMap<String,Any?>()
        for (argument in arguments) {
            map += argument.id to argument.load(section)
        }
        return@withContext map
    }

}