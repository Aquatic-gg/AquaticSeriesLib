package xyz.larkyy.aquaticfarming.crop.placed

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticfarming.crop.AbstractCrop

abstract class AbstractPlacedCrop(
    val location: Location,
    val cropId: String,
    val age: Int
) {

    val crop: AbstractCrop?
        get() {
            return AbstractAquaticFarming.instance?.cropRegistryManager?.allCrops()?.get(cropId)
        }

    abstract fun accelerate(incremental: Int = 1)
    abstract fun destroy()
    abstract fun dropLoot(player: Player, tool: ItemStack)

    abstract fun onBreak(player: Player, tool: ItemStack)

    abstract fun onHarvest(player: Player, tool: ItemStack)
}