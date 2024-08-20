package gg.aquatic.aquaticseries.lib.nms.listener

import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetContentPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetSlotPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundOpenScreenPacket

abstract class AbstractPacketListener {

    open fun onClientboundContainerSetContentPacket(event: PacketEvent<WrappedClientboundContainerSetContentPacket>) {}

    open fun onClientboundContainerSetSlotPacket(event: PacketEvent<WrappedClientboundContainerSetSlotPacket>) {}

    open fun onClientboundOpenScreenPacket(event: PacketEvent<WrappedClientboundOpenScreenPacket>) {}

}