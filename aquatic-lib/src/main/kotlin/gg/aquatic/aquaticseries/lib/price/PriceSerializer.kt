package gg.aquatic.aquaticseries.lib.price

import gg.aquatic.aquaticseries.lib.util.argument.ArgumentSerializer
import org.bukkit.configuration.ConfigurationSection

object PriceSerializer {

    inline fun <reified T: ConfiguredPrice<out Any>> fromSection(section: ConfigurationSection): T? {
        val type = section.getString("type") ?: return null
        val action = PriceTypes.types[type]
        if (action == null) {
            println("[AquaticSeriesLib] Action type $type does not exist!")
            return null
        }

        val arguments = action.arguments()
        val args = ArgumentSerializer.load(section, arguments)

        val configuredPrice = ConfiguredPrice(action, args)

        if (configuredPrice !is T) {
            return null
        }
        return configuredPrice
    }

    inline fun <reified T: ConfiguredPrice<out Any>> fromSections(sections: List<ConfigurationSection>): List<T> {
        return sections.mapNotNull { fromSection(it) }
    }

}