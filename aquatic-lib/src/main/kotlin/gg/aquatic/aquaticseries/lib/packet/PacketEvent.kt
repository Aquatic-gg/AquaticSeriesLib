package gg.aquatic.aquaticseries.lib.packet

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.nms.listener.AbstractPacketListener
import gg.aquatic.aquaticseries.lib.nms.listener.PacketEvent
import gg.aquatic.aquaticseries.lib.nms.packet.*

inline fun packetListener(crossinline consumer: PacketEvent<*>.() -> Unit) {
    val listener = object : AbstractPacketListener() {
        override fun onClientboundDisguisedChatPacket(event: PacketEvent<WrappedClientboundDisguisedChatPacket>) {
            consumer(event)
        }

        override fun onClientboundSystemChatPacket(event: PacketEvent<WrappedClientboundSystemChatPacket>) {
            consumer(event)
        }

        override fun onClientboundPlayerChatPacket(event: PacketEvent<WrappedClientboundPlayerChatPacket>) {
            consumer(event)
        }

        override fun onClientboundContainerSetContentPacket(event: PacketEvent<WrappedClientboundContainerSetContentPacket>) {
            consumer(event)
        }

        override fun onClientboundContainerSetSlotPacket(event: PacketEvent<WrappedClientboundContainerSetSlotPacket>) {
            consumer(event)
        }

        override fun onClientboundOpenScreenPacket(event: PacketEvent<WrappedClientboundOpenScreenPacket>) {
            consumer(event)
        }
    }

    AquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().registerListener(listener)
}