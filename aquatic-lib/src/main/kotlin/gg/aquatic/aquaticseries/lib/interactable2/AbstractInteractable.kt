package gg.aquatic.aquaticseries.lib.interactable2

import gg.aquatic.aquaticseries.lib.interactable2.base.InteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.PersistentInteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.TempInteractableBase
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.Location
import java.util.function.BiConsumer

abstract class AbstractInteractable<B>(
    val base: B
) where B : WorldObject, B: InteractableBase {

    abstract val onInteract: BiConsumer<SpawnedInteractable<*>,InteractableInteractEvent>

    fun register() {
        if (base is PersistentInteractableBase) {
            base.initialize(this)
        } else if (base is TempInteractableBase) {
            base.initialize(this)
        }
    }

    abstract fun fits(location: Location): Boolean

    abstract val id: String

    abstract fun spawn(location: Location, register: Boolean): AbstractSpawnedInteractable<*>
    abstract fun spawnPacket(location: Location, audienceList: AudienceList, register: Boolean): AbstractSpawnedPacketInteractable<*>

}