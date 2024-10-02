package gg.aquatic.aquaticseries.lib.item2

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.item2.factories.HDBFactory
import gg.aquatic.aquaticseries.lib.item2.factories.IAFactory
import gg.aquatic.aquaticseries.lib.item2.factories.MMFactory
import gg.aquatic.aquaticseries.lib.item2.factories.OraxenFactory
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object ItemHandler {

    val factories: MutableMap<String, Factory> = mutableMapOf(
        "MYTHICITEM" to MMFactory,
        "ORAXEN" to OraxenFactory,
        "HDB" to HDBFactory,
        "ITEMSADDER" to IAFactory
    )

    //val itemRegistry = java.util.HashMap<String, AquaticItem>()

    val NAMESPACE_KEY = NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, "Custom_Item_Registry")

    fun create(
        namespace: String,
        name: String?,
        description: MutableList<String>?,
        amount: Int,
        modeldata: Int,
        enchantments: MutableMap<String, Int>?,
        flags: MutableList<ItemFlag>?,
        spawnerEntityType: EntityType?
    ): AquaticItem? {
        val itemStack = if (namespace.contains(":")) {
            val id = namespace.split(":").first().uppercase()
            val factory = factories[id] ?: return null
            factory.create(namespace.substring(id.length + 1))
        } else {
            ItemStack(Material.valueOf(namespace.uppercase()))
        } ?: return null

        return create(itemStack, name, description, amount, modeldata, enchantments, flags, spawnerEntityType)
    }

    fun create(
        item: ItemStack,
        name: String? = null,
        description: MutableList<String>? = null,
        amount: Int = 1,
        modeldata: Int = -1,
        enchantments: MutableMap<String, Int>? = null,
        flags: MutableList<ItemFlag>? = null,
        spawnerEntityType: EntityType? = null
    ): AquaticItem {
        return AquaticItem(
            item,
            name,
            description,
            amount,
            modeldata,
            enchantments,
            flags,
            spawnerEntityType
        )
    }

    fun loadFromYaml(section: ConfigurationSection?): AquaticItem? {
        section ?: return null
        val material = section.getString("material", "STONE")!!
        var lore: MutableList<String>? = null
        if (section.contains("lore")) {
            lore = section.getStringList("lore")
        }
        val enchantments: MutableMap<String, Int> = HashMap()
        if (section.contains("enchants")) {
            for (str in section.getStringList("enchants")) {
                val strs = str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (strs.size < 2) {
                    continue
                }
                val enchantment = strs[0]
                val level = strs[1].toInt()
                enchantments[enchantment] = level
            }
        }
        val flags: MutableList<ItemFlag> = ArrayList()
        if (section.contains("flags")) {
            for (flag in section.getStringList("flags")) {
                val itemFlag = ItemFlag.valueOf(flag.uppercase())
                flags.add(itemFlag)
            }
        }
        val spawnerEntityType = section.getString("entity-type")?.let { EntityType.valueOf(it.uppercase()) }
        return create(
            material,
            section.getString("display-name"),
            lore,
            section.getInt("amount", 1),
            section.getInt("model-data"),
            enchantments,
            flags,
            spawnerEntityType
        )
    }

    interface Factory {

        fun create(id: String): ItemStack?

    }

}