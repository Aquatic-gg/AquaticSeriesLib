package gg.aquatic.aquaticseries.lib.fake

import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import java.util.function.Consumer

class PacketBlock(
    override val location: Location,
    val blockData: BlockData,
    override val audience: AudienceList,
    val onInteract: Consumer<PlayerInteractEvent>
): AbstractPacketObject() {

    init {
        FakeObjectHandler.registry.registerBlock(this)
    }

    override fun sendDespawnPacket(vararg players: Player) {
        for (player in players) {
            player.sendBlockChange(location, location.block.blockData)
        }
    }

    override fun sendSpawnPacket(vararg players: Player) {
        for (player in players) {
            player.sendBlockChange(location, blockData)
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

}