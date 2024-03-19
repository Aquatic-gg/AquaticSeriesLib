package xyz.larkyy.aquaticseries.interactable.impl.block

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import xyz.larkyy.aquaticseries.register

class BlockInteractableHandler private constructor(){

    val registry = HashMap<String,BlockInteractable>()

    init {
        Listeners().register()
    }

    companion object {

        private var _INSTANCE: BlockInteractableHandler? = null

        val INSTANCE: BlockInteractableHandler
            get() {
                var instance = _INSTANCE
                if (instance == null) {
                    instance = BlockInteractableHandler()
                    _INSTANCE = instance
                    return instance
                } else {
                    return instance
                }
            }
    }

    inner class Listeners: Listener {

        @EventHandler
        fun onInteract(playerInteractEvent: PlayerInteractEvent) {
            val block = playerInteractEvent.clickedBlock ?: return
            val blockInteractable = BlockInteractable.get(block) ?: return
            blockInteractable.onInteract.accept(playerInteractEvent)
        }

    }

}