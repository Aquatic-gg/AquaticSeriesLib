package gg.aquatic.aquaticseries.lib.fake.event

import gg.aquatic.aquaticseries.lib.fake.PacketEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action

class PacketEntityInteractEvent(

    val player: Player,
    val packetEntity: PacketEntity,
    val interaction: Action

): Event() {

    companion object {
        @JvmStatic
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