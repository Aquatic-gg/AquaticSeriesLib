package gg.aquatic.aquaticseries.lib.workload

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkUnloadEvent

class ChunkWorkloadHandler private constructor(){

    val workloads = HashMap<Chunk,MutableList<ChunkWorkload>>()

    companion object {
        private var _INSTANCE: ChunkWorkloadHandler? = null
        val INSTANCE: ChunkWorkloadHandler
            get() {
                var inst = _INSTANCE
                if (inst == null) {
                    inst = ChunkWorkloadHandler()
                    _INSTANCE = inst
                }
                return inst
            }
    }

    init {
        Bukkit.getPluginManager().registerEvents(Listeners(), AbstractAquaticSeriesLib.INSTANCE.plugin)
    }

    private inner class Listeners: Listener {
        @EventHandler
        fun onChunkUnload(event: ChunkUnloadEvent) {
            val wls = workloads[event.chunk] ?: return
            for (wl in wls) {
                wl.cancelAll()
            }
        }
    }

}