package gg.aquatic.aquaticseries.lib.packet

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PacketHandler: IFeature {
    override val type: Features = Features.PACKET_LISTENER

    override fun initialize(lib: AquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(Listeners(), lib.plugin)
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            AquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().inject(onlinePlayer)
        }
    }

    class Listeners : Listener {

        @EventHandler
        fun PlayerJoinEvent.onJoin() {
            AquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().inject(player)
        }

        @EventHandler
        fun PlayerQuitEvent.onQuit() {
            AquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().eject(player)
        }

    }
}