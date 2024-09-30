package gg.aquatic.aquaticseries.lib.interactable2

import com.ticxo.modelengine.api.events.BaseEntityInteractEvent
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.impl.meg.MegInteractableDummy
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object InteractableHandler: IFeature {

    override val type: Features = Features.INTERACTABLES

    override fun initialize(lib: AquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(Listeners(),lib.plugin)
        if (Bukkit.getPluginManager().getPlugin("ModelEngine") != null) {
            lib.plugin.server.pluginManager.registerEvents(MegListeners(),lib.plugin)
        }
    }

    class MegListeners: Listener {

        @EventHandler
        fun onMegInteract(event: BaseEntityInteractEvent) {
            val dummy = event.baseEntity as? MegInteractableDummy ?: return
            val interactable = dummy.interactable

            if (event.action == BaseEntityInteractEvent.Action.INTERACT_ON) return
            if (event.slot == EquipmentSlot.OFF_HAND) return

            val intEvent = InteractableInteractEvent(
                event.player, if (event.action == BaseEntityInteractEvent.Action.ATTACK) Action.LEFT_CLICK_AIR else Action.RIGHT_CLICK_AIR, dummy.location, interactable
            )
            interactable.onInteract(intEvent)
        }
    }

    class Listeners: Listener {

        @EventHandler
        fun onInteract(event: PlayerInteractEvent) {
            val block = event.clickedBlock ?: return

            val spawnedWorldObject = WorldObjectHandler.getSpawnedObject(block.location) ?: return
            if (spawnedWorldObject !is SpawnedInteractableBase<*>) return
            for (value in spawnedWorldObject.appliedInteractables.values) {
                if (event.hand == EquipmentSlot.OFF_HAND) return
                val intEvent = InteractableInteractEvent(
                    event.player, event.action, event.clickedBlock?.location, value
                )
                value.onInteract(intEvent)
                if (intEvent.cancelled) {
                    event.isCancelled = true
                }
            }
        }

        @EventHandler
        fun onBlockBreak(event: BlockBreakEvent) {
            val block = event.block
            val spawnedWorldObject = WorldObjectHandler.getSpawnedObject(block.location)
            if (spawnedWorldObject !is SpawnedInteractableBase<*>) return
            for (value in spawnedWorldObject.appliedInteractables.values) {
                val intEvent = InteractableInteractEvent(
                    event.player, Action.LEFT_CLICK_BLOCK, event.block.location, value
                )
                value.onInteract(intEvent)
                if (intEvent.cancelled) {
                    event.isCancelled = true
                }
            }
        }
    }
}