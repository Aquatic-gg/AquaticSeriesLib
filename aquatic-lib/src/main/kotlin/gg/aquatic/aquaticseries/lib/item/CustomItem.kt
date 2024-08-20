package gg.aquatic.aquaticseries.lib.item

import gg.aquatic.aquaticseries.lib.displayName
import gg.aquatic.aquaticseries.lib.lore
import gg.aquatic.aquaticseries.lib.setSpawnerType
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

abstract class CustomItem(

) {
    abstract val name: String?
    abstract val description: MutableList<String>?
    abstract val amount: Int
    abstract val modelData: Int
    abstract val enchantments: MutableMap<Enchantment, Int>?
    abstract val flags: MutableList<ItemFlag>?
    abstract val spawnerEntityType: EntityType?

    var registryId: String? = null

    fun giveItem(player: Player) {
        giveItem(player, amount)
    }

    fun giveItem(player: Player, amount: Int) {
        val iS = getItem()
        iS.amount = amount

        player.inventory.addItem(iS)
    }

    fun getItem(): ItemStack {
        val iS = getUnmodifiedItem()
        val im = iS.itemMeta ?: return iS

        name?.apply {
            im.displayName(this.toAquatic())
        }

        description?.apply {
            im.lore(this.toAquatic())
        }

        if (modelData > 0) {
            im.setCustomModelData(modelData)
        }

        flags?.apply {
            im.addItemFlags(*this.toTypedArray())
        }

        registryId?.let {
            im.persistentDataContainer.set(CustomItemHandler.NAMESPACE_KEY, PersistentDataType.STRING, it)
        }
        spawnerEntityType?.apply {
            if (iS.type == Material.SPAWNER) {
                im.setSpawnerType(this)
            }
        }

        iS.itemMeta = im

        enchantments?.apply {
            if (iS.type == Material.ENCHANTED_BOOK) {
                val esm = im as EnchantmentStorageMeta
                this.forEach { (t, u) ->
                    esm.addStoredEnchant(t, u, true)
                }
                iS.itemMeta = esm
            } else {
                iS.itemMeta = im
                iS.addUnsafeEnchantments(this)
            }
        }

        iS.amount = amount
        return iS
    }

    fun register(id: String) {
        if (this.registryId != null) return
        this.registryId = id
        CustomItemHandler.itemRegistry[id] = this
    }

    abstract fun getUnmodifiedItem(): ItemStack

    companion object {

        val customItemHandler: CustomItemHandler = CustomItemHandler

        private fun getEnchantmentByString(ench: String): Enchantment? {
            return Enchantment.getByKey(NamespacedKey.minecraft(ench.lowercase(Locale.getDefault())))
        }

        fun get(id: String): CustomItem? {
            return CustomItemHandler.itemRegistry[id]
        }

        fun get(itemStack: ItemStack): CustomItem? {
            val pdc = itemStack.itemMeta?.persistentDataContainer ?: return null
            if (!pdc.has(CustomItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)) return null
            val id = pdc.get(CustomItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)
            return CustomItemHandler.itemRegistry[id]
        }

        fun create(
            namespace: String,
            name: String?,
            description: MutableList<String>?,
            amount: Int,
            modeldata: Int,
            enchantments: MutableMap<Enchantment, Int>?,
            flags: MutableList<ItemFlag>?,
            spawnerEntityType: EntityType?
        ): CustomItem {
            return CustomItemHandler.getCustomItem(
                namespace,
                name,
                description,
                amount,
                modeldata,
                enchantments,
                flags,
                spawnerEntityType
            )
        }

        fun loadFromYaml(section: ConfigurationSection?): CustomItem? {
            section ?: return null
            var lore: MutableList<String>? = null
            if (section.contains("lore")) {
                lore = section.getStringList("lore")
            }
            val enchantments: MutableMap<Enchantment, Int> = HashMap()
            if (section.contains("enchants")) {
                for (str in section.getStringList("enchants")) {
                    val strs = str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (strs.size < 2) {
                        continue
                    }
                    val enchantment = getEnchantmentByString(strs[0]) ?: continue
                    val level = strs[1].toInt()
                    enchantments[enchantment] = level
                }
            }
            val flags: MutableList<ItemFlag> = ArrayList()
            if (section.contains("flags")) {
                for (flag in section.getStringList("flags")) {
                    val itemFlag = ItemFlag.valueOf(flag.uppercase(Locale.getDefault()))
                    flags.add(itemFlag)
                }
            }
            val spawnerEntityType = section.getString("entity-type")?.let { EntityType.valueOf(it.uppercase()) }
            return create(
                section.getString("material", "STONE")!!,
                section.getString("display-name"),
                lore,
                section.getInt("amount", 1),
                section.getInt("model-data"),
                enchantments,
                flags,
                spawnerEntityType
            )
        }
    }

}