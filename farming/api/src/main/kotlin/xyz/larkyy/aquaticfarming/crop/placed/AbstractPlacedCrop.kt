package xyz.larkyy.aquaticfarming.crop.placed

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticfarming.crop.AbstractCrop
import java.util.UUID

abstract class AbstractPlacedCrop(
    val locations: List<Location>,
    val baseLocation: Location,
    val cropId: String,
    val age: Int
) {

    val uuid: UUID = UUID.randomUUID()

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