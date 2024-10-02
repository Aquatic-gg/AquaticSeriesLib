package gg.aquatic.aquaticseries.lib.item

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.displayName
import gg.aquatic.aquaticseries.lib.lore
import gg.aquatic.aquaticseries.lib.setSpawnerType
import gg.aquatic.aquaticseries.lib.toAquatic
import net.advancedplugins.ae.api.AEAPI
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
    abstract val enchantments: MutableMap<String, Int>?
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
                for ((ench, level) in this) {
                    if (ench.uppercase().startsWith("AE-")) continue
                    if (ench.uppercase() == "AE-SLOTS") continue

                    getEnchantmentByString(ench)?.apply {
                        esm.addStoredEnchant(this, level, true)
                    }
                }
                iS.itemMeta = esm
            } else {
                iS.itemMeta = im
                for ((ench, level) in this) {
                    if (ench.uppercase() == "AE-SLOTS") {
                        AEAPI.setTotalSlots(
                            iS,
                            level
                        )
                        continue
                    }
                    if (ench.uppercase().startsWith("AE-")) {
                        AEAPI.applyEnchant(ench.substringBefore("AE-"), level, iS)
                        continue
                    }
                    getEnchantmentByString(ench)?.apply {
                        iS.addUnsafeEnchantment(this,level)
                    }
                }
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

    class Builder(var type: String) {

        private var lore: MutableList<AquaticString>? = null
        private var displayName: AquaticString? = null
        private var amount: Int = 1
        private var modelData: Int = -1
        private var enchantments: MutableMap<String, Int>? = null
        private var flags: MutableList<ItemFlag>? = null
        private var spawnerEntityType: EntityType? = null

        fun lore(lore: MutableList<AquaticString>?): Builder {
            this.lore = lore
            return this
        }

        fun displayName(displayName: AquaticString?): Builder {
            this.displayName = displayName
            return this
        }

        fun amount(amount: Int): Builder {
            this.amount = amount
            return this
        }

        fun modelData(modelData: Int): Builder {
            this.modelData = modelData
            return this
        }

        fun enchantments(enchantments: MutableMap<String, Int>?): Builder {
            this.enchantments = enchantments
            return this
        }

        fun flags(flags: MutableList<ItemFlag>?): Builder {
            this.flags = flags
            return this
        }

        fun spawnerEntityType(spawnerEntityType: EntityType?): Builder {
            this.spawnerEntityType = spawnerEntityType
            return this
        }

        constructor(material: Material) : this(material.toString())

        fun build(): CustomItem {
            return create(
                type,
                displayName?.string,
                lore?.map { it.string }?.toMutableList(),
                amount,
                modelData,
                enchantments,
                flags,
                spawnerEntityType
            )
        }
    }

    companion object {

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
            enchantments: MutableMap<String, Int>?,
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