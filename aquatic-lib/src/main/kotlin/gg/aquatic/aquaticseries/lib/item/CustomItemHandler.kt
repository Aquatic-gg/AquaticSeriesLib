package gg.aquatic.aquaticseries.lib.item

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.item.factory.ItemFactory
import gg.aquatic.aquaticseries.lib.item.factory.impl.MMFactory
import gg.aquatic.aquaticseries.lib.item.factory.impl.OraxenFactory
import gg.aquatic.aquaticseries.lib.item.impl.VanillaItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import java.util.*
import kotlin.collections.HashMap

object CustomItemHandler {

    private val itemRegistries = HashMap<String, ItemFactory>().apply {
        put("mythicitem", MMFactory)
        put("oraxen", OraxenFactory)
    }

    val itemRegistry = HashMap<String, CustomItem>()

    val NAMESPACE_KEY = NamespacedKey(AquaticSeriesLib.INSTANCE.plugin,"Custom_Item_Registry")

    fun getCustomItem(
        namespace: String,
        name: String?,
        description: MutableList<String>?,
        amount: Int,
        modeldata: Int,
        enchantments: MutableMap<Enchantment, Int>?,
        flags: MutableList<ItemFlag>?
    ): CustomItem {
        val strs = namespace.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val provider = strs[0].lowercase(Locale.getDefault())
        val factory: ItemFactory? = itemRegistries[provider]
        if (strs.size == 1 || factory == null) {
            return VanillaItem(
                Material.valueOf(strs[0].uppercase(Locale.getDefault())),
                name,
                description,
                amount,
                modeldata,
                enchantments,
                flags
            )
        }
        val identifier = namespace.substring(provider.length + 1)
        return factory.create(identifier, name, description, amount, modeldata, enchantments, flags)
    }

}