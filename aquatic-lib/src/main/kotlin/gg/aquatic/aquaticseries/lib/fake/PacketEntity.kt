package gg.aquatic.aquaticseries.lib.fake

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.fake.event.PacketEntityInteractEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.function.Consumer

class PacketEntity(
    override val location: Location,
    val entityId: Int,
    override val audience: AquaticAudience,
    val onInteract: Consumer<PacketEntityInteractEvent>
): AbstractPacketObject() {

    init {
        FakeObjectHandler.registry.registerEntity(this)
    }

    override fun sendDespawnPacket(vararg players: Player) {
        AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.despawnEntity(listOf(entityId), WhitelistAudience(players.map { it.uniqueId }.toMutableList()))
    }

    override fun sendSpawnPacket(vararg players: Player) {
        for (player in players) {
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.resendEntitySpawnPacket(player, entityId)
        }
    }

    fun update() {
        for (player in audience.uuids.mapNotNull { Bukkit.getPlayer(it) }) {
            sendSpawnPacket(player)
        }
    }


    override fun spawn() {
        for (uuid in audience.uuids) {
            val player = Bukkit.getPlayer(uuid) ?: continue
            sendSpawnPacket(player)
        }
        spawned = true
    }

    override fun despawn() {
        for (uuid in audience.uuids) {
            val player = Bukkit.getPlayer(uuid) ?: continue
            sendDespawnPacket(player)
        }
        spawned = false
    }

    val bukkitEntity: org.bukkit.entity.Entity?
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.getEntity(entityId)
        }
}