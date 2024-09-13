package gg.aquatic.aquaticseries.lib.nms

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.nms.listener.PacketListenerAdapter
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.util.function.Consumer

interface NMSAdapter {

    fun packetListenerAdapter(): PacketListenerAdapter

    fun spawnEntity(location: Location, type: String, audience: AquaticAudience, factory: Consumer<Entity>): Int
    fun getEntity(id: Int): Entity?
    fun despawnEntity(ids: List<Int>, audience: AquaticAudience)
    fun updateEntity(id: Int, factory: Consumer<Entity>, audience: AquaticAudience)
    fun updateEntityVelocity(id: Int, velocity: Vector, audience: AquaticAudience)
    fun teleportEntity(id: Int, location: Location, audience: AquaticAudience)
    fun moveEntity(id: Int, location: Location, audience: AquaticAudience)
    fun setSpectatorTarget(id: Int, audience: AquaticAudience)
    fun setGamemode(gamemode: GameMode, player: Player)
    fun setPlayerInfoGamemode(gamemode: GameMode, player: Player)
    fun setContainerItem(player: Player, itemStack: ItemStack, slot: Int)
    fun setInventoryContent(audience: AquaticAudience, inventoryType: InventoryType, content: Collection<ItemStack> ,activeItem: ItemStack?)
    fun sendTitleUpdate(player: Player, newTitle: AquaticString)
    fun resendEntitySpawnPacket(player: Player, entityId: Int)
    fun modifyTabCompletion(action: TabCompletionAction, list: List<String>, vararg players: Player)

    enum class InventoryType {
        PLAYER,
        TOP
    }

    enum class TabCompletionAction {
        ADD,
        REMOVE,
        SET
    }
}