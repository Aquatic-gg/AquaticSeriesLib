package gg.aquatic.aquaticseries.lib.item2

import gg.aquatic.aquaticseries.lib.util.displayName
import gg.aquatic.aquaticseries.lib.util.lore
import gg.aquatic.aquaticseries.lib.util.setSpawnerType
import gg.aquatic.aquaticseries.lib.util.toAquatic
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
import java.util.*

class AquaticItem(
    private val item: ItemStack,
    val name: String?,
    val description: MutableList<String>?,
    val amount: Int,
    val modelData: Int,
    val enchantments: MutableMap<String, Int>?,
    val flags: MutableList<ItemFlag>?,
    val spawnerEntityType: EntityType?,
) {

    /*
    val registryId: String?
        get() {
            val meta = item.itemMeta
            val pdc = meta?.persistentDataContainer ?: return null
            return pdc.get(ItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)
        }

    fun register(namespace: String, id: String): Boolean {
        if (this.registryId != null) return false
        val meta = item.itemMeta ?: return false
        meta.persistentDataContainer.set(ItemHandler.NAMESPACE_KEY, PersistentDataType.STRING, "$namespace:$id")
        this.item.itemMeta = meta
        ItemHandler.itemRegistry[this.registryId!!] = this
        return true
    }
     */

    fun giveItem(player: Player) {
        giveItem(player, amount)
    }

    fun giveItem(player: Player, amount: Int) {
        val iS = getItem()
        iS.amount = amount

        player.inventory.addItem(iS)
    }

    fun getUnmodifiedItem(): ItemStack {
        return item
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
                        iS.addUnsafeEnchantment(this, level)
                    }
                }
            }
        }

        iS.amount = amount
        return iS
    }

    companion object {
        private fun getEnchantmentByString(ench: String): Enchantment? {
            return Enchantment.getByKey(NamespacedKey.minecraft(ench.lowercase(Locale.getDefault())))
        }

        /*
        fun get(id: String): AquaticItem? {
            return ItemHandler.itemRegistry[id]
        }

        fun get(itemStack: ItemStack): AquaticItem? {
            val pdc = itemStack.itemMeta?.persistentDataContainer ?: return null
            if (!pdc.has(ItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)) return null
            val id = pdc.get(ItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)
            return ItemHandler.itemRegistry[id]
        }
         */

        fun loadFromYaml(section: ConfigurationSection?): AquaticItem? {
            return ItemHandler.loadFromYaml(section)
        }
    }
}