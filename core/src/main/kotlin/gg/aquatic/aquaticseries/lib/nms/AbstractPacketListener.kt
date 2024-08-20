package gg.aquatic.aquaticseries.lib.nms

import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetContentPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetSlotPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundOpenScreenPacket

abstract class AbstractPacketListener {

    open fun onClientboundContainerSetContentPacket(packet: WrappedClientboundContainerSetContentPacket) {}

    open fun onClientboundContainerSetSlotPacket(packet: WrappedClientboundContainerSetSlotPacket) {}

    open fun onClientboundOpenScreenPacket(packet: WrappedClientboundOpenScreenPacket) {}

}