package xyz.larkyy.aquaticfarming.harvestable

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.aquaticseries.interactable.event.InteractablesChunkLoadEvent
import xyz.larkyy.aquaticseries.interactable.event.InteractablesChunkUnloadEvent
import java.util.UUID

class HarvestableManager {

    val spawnedHarvestables = HashMap<UUID,SpawnedHarvestable>()
    val harvestables = HashMap<String,Harvestable>()

    fun tickHarvestables() {
        for (entry in spawnedHarvestables) {
            entry.value.tick()
        }
    }

    fun registerListeners(plugin: JavaPlugin) {
        plugin.server.pluginManager.registerEvents(Listeners(),plugin)
    }

    inner class Listeners: Listener {

        @EventHandler
        fun onInteractableChunkLoad(event: InteractablesChunkLoadEvent) {
            for (interactable in event.interactables) {
                val harvestable = Harvestable.get(interactable) ?: continue
                val spawned = harvestable.fromInteractable(interactable) ?: continue
                spawnedHarvestables[spawned.uuid] = spawned
            }
        }


        @EventHandler
        fun onInteractableChunkUnload(event: InteractablesChunkUnloadEvent) {
            for (interactable in event.interactables) {
                val uuid = SpawnedHarvestable.getUUID(interactable) ?: continue
                spawnedHarvestables -= uuid
            }
        }

        @EventHandler
        fun onInteract(event: PlayerInteractEvent) {
            val block = event.clickedBlock ?: return
            if (event.hand == EquipmentSlot.OFF_HAND) return
            if (event.action != Action.RIGHT_CLICK_BLOCK) return
            val item = event.item ?: return
            val seed = HarvestableSeed.get(item) ?: return
            val loc = block.location.clone().add(event.blockFace.direction)
            if (!seed.harvestable.canBeSpawned(loc)) {
                event.isCancelled = true
                event.player.sendMessage("You cannot place this here!")
                return
            }
            seed.harvestable.spawn(loc)
        }
    }
}