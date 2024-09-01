package gg.aquatic.aquaticseries.lib.betterinventory

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.nms.listener.AbstractPacketListener
import gg.aquatic.aquaticseries.lib.nms.listener.PacketEvent
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetContentPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetSlotPacket
import org.bukkit.event.Listener

object InventoryHandler: IFeature {
    override val type: Features = Features.INVENTORIES

    override fun initialize(lib: AbstractAquaticSeriesLib) {

    }

    class Listeners: Listener {

    }

    class PacketListeners: AbstractPacketListener() {

        override fun onClientboundContainerSetContentPacket(event: PacketEvent<WrappedClientboundContainerSetContentPacket>) {

        }

        override fun onClientboundContainerSetSlotPacket(event: PacketEvent<WrappedClientboundContainerSetSlotPacket>) {

        }

    }
}