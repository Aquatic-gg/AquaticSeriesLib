package gg.aquatic.aquaticseries.lib.nms

import gg.aquatic.aquaticseries.lib.util.AbstractAudience
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.util.function.Consumer

interface NMSAdapter {

    fun spawnEntity(location: Location, type: String, audience: AbstractAudience, factory: Consumer<Entity>): Int
    fun getEntity(id: Int): Entity?
    fun despawnEntity(ids: List<Int>, audience: AbstractAudience)
    fun updateEntity(id: Int, factory: Consumer<Entity>, audience: AbstractAudience)
    fun updateEntityVelocity(id: Int, velocity: Vector, audience: AbstractAudience)
    fun teleportEntity(id: Int, location: Location, audience: AbstractAudience)
    fun moveEntity(id: Int, location: Location, audience: AbstractAudience)
    fun setSpectatorTarget(id: Int, target: Int, audience: AbstractAudience)
    fun setGamemode(gamemode: GameMode, player: Player)
    fun setPlayerInfoGamemode(gamemode: GameMode, player: Player)
    fun setContainerItem(player: Player, itemStack: ItemStack, slot: Int)
    fun setInventoryContent(audience: AbstractAudience, inventoryType: InventoryType, content: Collection<ItemStack> ,activeItem: ItemStack?)

    enum class InventoryType {
        PLAYER,
        TOP
    }
}