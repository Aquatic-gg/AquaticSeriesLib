package xyz.larkyy.aquaticfarming.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockBreakEvent
import xyz.larkyy.aquaticfarming.harvestable.TreeBlockData

class TreeBlockBreakEvent(
    val data: TreeBlockData,
    val originalEvent: BlockBreakEvent
): Event() {

    companion object {
        private val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

}