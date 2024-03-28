package xyz.larkyy.aquaticfarming.harvestable

import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticseries.item.CustomItem

class HarvestableSeed(
    val item: CustomItem,
    val harvestable: Harvestable
) {

    companion object {

        fun get(item: ItemStack): HarvestableSeed? {
            val im = item.itemMeta ?: return null
            val pdc = im.persistentDataContainer
            if (!pdc.has(Harvestable.namespace, PersistentDataType.STRING)) return null
            val id = pdc.get(Harvestable.namespace, PersistentDataType.STRING)
            return AbstractAquaticFarming.instance!!.harvestableManager.harvestables[id]?.seed
        }
    }
}