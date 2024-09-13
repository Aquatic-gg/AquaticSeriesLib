package gg.aquatic.aquaticseries.lib.nms.listener

import gg.aquatic.aquaticseries.lib.nms.packet.*

abstract class AbstractPacketListener {

    open fun onClientboundContainerSetContentPacket(event: PacketEvent<WrappedClientboundContainerSetContentPacket>) {}

    open fun onClientboundContainerSetSlotPacket(event: PacketEvent<WrappedClientboundContainerSetSlotPacket>) {}

    open fun onClientboundOpenScreenPacket(event: PacketEvent<WrappedClientboundOpenScreenPacket>) {}
    open fun onClientboundPlayerChatPacket(event: PacketEvent<WrappedClientboundPlayerChatPacket>) {}
    open fun onClientboundSystemChatPacket(event: PacketEvent<WrappedClientboundSystemChatPacket>) {}
    open fun onClientboundDisguisedChatPacket(event: PacketEvent<WrappedClientboundDisguisedChatPacket>) {}

}