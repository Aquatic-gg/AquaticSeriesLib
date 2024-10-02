package gg.aquatic.aquaticseries.lib.item2.factories

import dev.lone.itemsadder.api.CustomStack
import gg.aquatic.aquaticseries.lib.item2.ItemHandler
import org.bukkit.inventory.ItemStack

object IAFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return CustomStack.getInstance(id)!!.itemStack
    }
}