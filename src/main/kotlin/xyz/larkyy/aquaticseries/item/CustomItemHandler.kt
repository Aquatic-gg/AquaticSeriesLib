package xyz.larkyy.aquaticseries.item

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.item.factory.ItemFactory
import xyz.larkyy.aquaticseries.item.factory.impl.MMFactory
import xyz.larkyy.aquaticseries.item.factory.impl.OraxenFactory
import xyz.larkyy.aquaticseries.item.impl.VanillaItem
import java.util.*
import kotlin.collections.HashMap

class CustomItemHandler {

    private val itemRegistries = HashMap<String, ItemFactory>().apply {
        put("mythicitem", MMFactory())
        put("oraxen", OraxenFactory())
    }

    val itemRegistry = HashMap<String,CustomItem>()

    companion object {
        val NAMESPACE_KEY = NamespacedKey(AquaticSeriesLib.INSTANCE.plugin,"Custom_Item_Registry")
    }

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