package gg.aquatic.aquaticseries.lib.packet

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PacketHandler: IFeature {
    override val type: Features = Features.PACKET_LISTENER

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(Listeners(), lib.plugin)
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().inject(onlinePlayer)
        }
    }

    class Listeners : Listener {

        @EventHandler
        fun PlayerJoinEvent.onJoin() {
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().inject(player)
        }

        @EventHandler
        fun PlayerQuitEvent.onQuit() {
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.packetListenerAdapter().eject(player)
        }

    }
}