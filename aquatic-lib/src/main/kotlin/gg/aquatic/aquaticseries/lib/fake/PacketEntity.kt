package gg.aquatic.aquaticseries.lib.fake

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.fake.event.PacketEntityInteractEvent
import gg.aquatic.aquaticseries.lib.interactable.AudienceList
import gg.aquatic.aquaticseries.lib.util.AbstractAudience
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.function.Consumer

class PacketEntity(
    override val location: Location,
    val entityId: Int,
    override val audience: AudienceList,
    val onInteract: Consumer<PacketEntityInteractEvent>
): AbstractPacketObject() {

    init {
        FakeObjectHandler.registry.registerEntity(this)
    }

    override fun sendDespawnPacket(vararg players: Player) {
        for (player in players) {
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.despawnEntity(listOf(entityId), AbstractAudience.SinglePlayerAudience(player))
        }

    }

    override fun sendSpawnPacket(vararg players: Player) {
        for (player in players) {
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.resendEntitySpawnPacket(player, entityId)
        }
    }


    override fun spawn() {
        for (uuid in audience.appliedTo) {
            val player = Bukkit.getPlayer(uuid) ?: continue
            sendSpawnPacket(player)
        }
        spawned = true
    }

    override fun despawn() {
        for (uuid in audience.appliedTo) {
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