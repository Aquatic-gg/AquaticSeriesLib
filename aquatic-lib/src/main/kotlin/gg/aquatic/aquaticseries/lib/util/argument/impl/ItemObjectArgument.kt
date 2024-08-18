package gg.aquatic.aquaticseries.lib.util.argument.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import gg.aquatic.aquaticseries.lib.util.argument.AbstractObjectArgumentSerializer
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import org.bukkit.configuration.ConfigurationSection

class ItemObjectArgument(id: String, defaultValue: CustomItem?, required: Boolean) : AquaticObjectArgument<CustomItem>(id, defaultValue,
    required
) {
    override val serializer: AbstractObjectArgumentSerializer<CustomItem?>
        get() {
            return Serializer
        }

    override fun load(section: ConfigurationSection): CustomItem? {
        return serializer.load(section, id) ?: defaultValue
    }

    object Serializer : AbstractObjectArgumentSerializer<CustomItem?>() {
        override fun load(section: ConfigurationSection, id: String): CustomItem? {
            return CustomItem.loadFromYaml(section.getConfigurationSection(id) ?: return null)
        }
    }
}