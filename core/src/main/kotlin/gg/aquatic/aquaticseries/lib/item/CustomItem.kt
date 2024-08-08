package gg.aquatic.aquaticseries.lib.item

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.persistence.PersistentDataType
import gg.aquatic.aquaticseries.format.color.ColorUtils
import java.util.*

abstract class CustomItem(

) {
    abstract val name: String?
    abstract val description: MutableList<String>?
    abstract val amount: Int
    abstract val modelData: Int
    abstract val enchantments: MutableMap<Enchantment,Int>?
    abstract val flags: MutableList<ItemFlag>?

    var registryId: String? = null

    fun giveItem(player: Player) {
        giveItem(player,amount)
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
            im.setDisplayName(ColorUtils.format(this))
        }

        description?.apply {
            im.lore = ColorUtils.format(this)
        }

        if (modelData > 0) {
            im.setCustomModelData(modelData)
        }

        flags?.apply {
            im.addItemFlags(*this.toTypedArray())
        }

        registryId?.let {
            im.persistentDataContainer.set(CustomItemHandler.NAMESPACE_KEY, PersistentDataType.STRING,it)
        }

        iS.itemMeta = im

        enchantments?.apply {
            if (iS.type == Material.ENCHANTED_BOOK) {
                val esm = im as EnchantmentStorageMeta
                this.forEach { (t, u) ->
                    esm.addStoredEnchant(t,u,true)
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
        customItemHandler.itemRegistry[id] = this
    }

    abstract fun getUnmodifiedItem(): ItemStack

    companion object {

        val customItemHandler: CustomItemHandler = CustomItemHandler

        private fun getEnchantmentByString(ench: String): Enchantment? {
            return Enchantment.getByKey(NamespacedKey.minecraft(ench.lowercase(Locale.getDefault())))
        }

        fun get(id: String): CustomItem? {
            return customItemHandler.itemRegistry[id]
        }
        fun get(itemStack: ItemStack): CustomItem? {
            val pdc = itemStack.itemMeta?.persistentDataContainer ?: return null
            if (!pdc.has(CustomItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)) return null
            val id = pdc.get(CustomItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)
            return customItemHandler.itemRegistry[id]
        }

        fun create(
            namespace: String,
            name: String?,
            description: MutableList<String>?,
            amount: Int,
            modeldata: Int,
            enchantments: MutableMap<Enchantment, Int>?,
            flags: MutableList<ItemFlag>?
        ): CustomItem {
            return customItemHandler.getCustomItem(namespace, name, description, amount, modeldata, enchantments, flags)
        }

        fun loadFromYaml(cfg: FileConfiguration, path: String): CustomItem? {
            if (!cfg.contains(path)) {
                return null
            }
            var lore: MutableList<String>? = null
            if (cfg.contains("$path.lore")) {
                lore = cfg.getStringList("$path.lore")
            }
            val enchantments: MutableMap<Enchantment, Int> = HashMap()
            if (cfg.contains("$path.enchants")) {
                for (str in cfg.getStringList("$path.enchants")) {
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
            if (cfg.contains("$path.flags")) {
                for (flag in cfg.getStringList("$path.flags")) {
                    val itemFlag = ItemFlag.valueOf(flag.uppercase(Locale.getDefault()))
                    flags.add(itemFlag)
                }
            }
            return create(
                cfg.getString("$path.material", "STONE")!!,
                cfg.getString("$path.display-name"),
                lore,
                cfg.getInt("$path.amount", 1),
                cfg.getInt("$path.model-data"),
                enchantments,
                flags
            )
        }
    }

}