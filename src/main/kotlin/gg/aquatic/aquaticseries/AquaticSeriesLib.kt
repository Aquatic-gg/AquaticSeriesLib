package gg.aquatic.aquaticseries

import com.google.gson.Gson
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import xyz.larkyy.aquaticseries.interactable.InteractableHandler
import xyz.larkyy.aquaticseries.awaiters.AbstractAwaiter
import xyz.larkyy.aquaticseries.awaiters.IAAwaiter
import xyz.larkyy.aquaticseries.awaiters.MEGAwaiter

class AquaticSeriesLib private constructor(val plugin: JavaPlugin, workloadDelay: Long) {

    val interactableHandler = InteractableHandler(workloadDelay)

    var enginesLoaded = false

    init {
        object : BukkitRunnable() {
            override fun run() {
                interactableHandler.registerListeners(plugin)
            }
        }.runTaskLater(plugin,1)

        val loaders = ArrayList<AbstractAwaiter>()
        if (Bukkit.getPluginManager().getPlugin("ModelEngine") != null) {
            val awaiter = MEGAwaiter(this)
            loaders += awaiter
            awaiter.future.thenRun {
                if (loaders.all { it.loaded }) {
                    onEnginesInit()
                }
            }
        }
        if (Bukkit.getPluginManager().getPlugin("ItemsAdder") != null) {
            val awaiter = IAAwaiter(this)
            loaders += awaiter
            awaiter.future.thenRun {
                if (loaders.all { it.loaded }) {
                    onEnginesInit()
                }
            }
        }
    }

    private fun onEnginesInit() {
        enginesLoaded = true
        interactableHandler.canRun = true
        interactableHandler.interactableWorkload.run()
    }

    companion object {

        val GSON = Gson()
        private var _INSTANCE: AquaticSeriesLib? = null
        val INSTANCE: AquaticSeriesLib
            get() {
                if (_INSTANCE == null) {
                    throw Exception("Library was not initialized! Use the init() method first!")
                }
                return _INSTANCE!!
            }

        fun init(plugin: JavaPlugin, workloadDelay: Long): AquaticSeriesLib {
            val instance = _INSTANCE
            if (instance != null) return instance
            _INSTANCE = AquaticSeriesLib(plugin, workloadDelay)
            return _INSTANCE!!
        }
    }

}