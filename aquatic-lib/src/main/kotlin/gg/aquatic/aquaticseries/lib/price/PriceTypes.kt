package gg.aquatic.aquaticseries.lib.price

import org.bukkit.configuration.ConfigurationSection
import gg.aquatic.aquaticseries.lib.price.player.impl.ItemPrice

object PriceTypes {

    val types = HashMap<String, AbstractPrice<*>>().apply {
        this += "item" to ItemPrice()
        //this += "vault" to VaultPrice()
    }

    operator fun get(id: String): AbstractPrice<*>? {
        return types[id]
    }

}