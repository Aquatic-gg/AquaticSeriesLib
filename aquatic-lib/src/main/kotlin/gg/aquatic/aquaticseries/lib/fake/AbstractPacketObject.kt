package gg.aquatic.aquaticseries.lib.fake

import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.Location
import org.bukkit.entity.Player

abstract class AbstractPacketObject(

) {
    abstract val location: Location
    abstract val audience: AudienceList

    var spawned: Boolean = false

    abstract fun sendDespawnPacket(vararg players: Player)

    abstract fun sendSpawnPacket(vararg players: Player)

    abstract fun spawn()

    abstract fun despawn()

}