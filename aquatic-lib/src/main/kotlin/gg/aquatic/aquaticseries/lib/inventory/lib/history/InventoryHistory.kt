package gg.aquatic.aquaticseries.lib.inventory.lib.history

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory
import java.util.UUID

class InventoryHistory {

    val history = HashMap<UUID, PlayerHistory>()

    fun addToHistory(player: Player, previous: CustomInventory?, ignore: Boolean) {
        history[player.uniqueId] = PlayerHistory(previous,ignore)
    }

    fun clearPlayerFromHistory(uuid: UUID) {
        val ph = history.remove(uuid) ?: return
        val previous = ph.previousInventory ?: return
        previous.history.clearPlayerFromHistory(uuid)
    }

    fun previous(player: Player): CustomInventory? {
        val ph = history[player.uniqueId] ?: return null
        return ph.previousInventory
    }

    fun getHistory(player: Player): PlayerHistory? {
        return history[player.uniqueId]
    }

    fun removeFromHistory(player: Player) {
        history.remove(player.uniqueId)
    }

    fun hasPrevious(player: Player): Boolean {
        return history.containsKey(player.uniqueId)
    }

}