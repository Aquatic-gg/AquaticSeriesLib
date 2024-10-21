package gg.aquatic.aquaticseries.lib.util.argument.impl

import gg.aquatic.aquaticseries.lib.util.argument.AbstractObjectArgumentSerializer
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.configuration.ConfigurationSection

class PrimitiveObjectArgument(id: String, defaultValue: Any?, required: Boolean) : AquaticObjectArgument<Any?>(id, defaultValue,
    required
) {
    override val serializer: AbstractObjectArgumentSerializer<Any?>
        get() {
            return Serializer
        }

    override suspend fun load(section: ConfigurationSection): Any? {
        return serializer.load(section, id) ?: defaultValue
    }

    object Serializer: AbstractObjectArgumentSerializer<Any?>() {
        override suspend fun load(section: ConfigurationSection, id: String): Any? = withContext(Dispatchers.IO) {
            return@withContext section.get(id)
        }
    }
}