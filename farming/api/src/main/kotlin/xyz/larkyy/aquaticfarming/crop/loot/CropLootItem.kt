package xyz.larkyy.aquaticfarming.crop.loot

import org.bukkit.inventory.ItemStack
import xyz.larkyy.aquaticseries.RangeAmount
import xyz.larkyy.aquaticseries.chance.IChance
import xyz.larkyy.aquaticseries.item.CustomItem

class CropLootItem(
    val item: CustomItem,
    val amount: RangeAmount,
    val chance: Double
): IChance {

    override fun chance(): Double {
        return chance
    }

    fun getRandomizedItem(): ItemStack {
        val iS = item.getItem().clone()
        iS.amount = amount.getRandomAmount
        return iS
    }
}