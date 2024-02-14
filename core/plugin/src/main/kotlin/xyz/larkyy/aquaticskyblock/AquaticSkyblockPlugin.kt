package xyz.larkyy.aquaticskyblock

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import org.bukkit.Bukkit

class AquaticSkyblockPlugin: AbstractAquaticSkyblockPlugin() {

    override fun onLoad() {

    }

    override fun onEnable() {
        Bukkit.getOnlinePlayers().forEach {
            val islandPlayer = SuperiorSkyblockAPI.getPlayer(it)
            val uuid = islandPlayer.island?.uniqueId?.toString() ?: "NULL"
            Bukkit.broadcastMessage("Player ${it.name} island: $uuid")
        }
    }

    override fun onDisable() {

    }

}