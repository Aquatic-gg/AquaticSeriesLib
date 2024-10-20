package gg.aquatic.aquaticseries.lib.item2

import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object ItemHandler {

    //val itemRegistry = java.util.HashMap<String, AquaticItem>()

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

    interface Factory {

        fun create(id: String): ItemStack?

    }

}