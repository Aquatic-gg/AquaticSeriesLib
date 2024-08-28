package gg.aquatic.aquaticseries.lib.displayentity

import gg.aquatic.aquaticseries.lib.fake.PacketEntity
import org.bukkit.entity.Player

class AquaticPacketDisplay(
    val packetEntity: PacketEntity,
    val display: AquaticDisplay
) {

    fun show(vararg players: Player) {
        packetEntity.sendSpawnPacket(*players)
    }
    fun hide(vararg players: Player) {
        packetEntity.sendDespawnPacket(*players)
    }

    fun update() {
        packetEntity.update()
    }

}