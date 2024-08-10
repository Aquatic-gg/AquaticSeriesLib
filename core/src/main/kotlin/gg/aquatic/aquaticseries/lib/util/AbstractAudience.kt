package gg.aquatic.aquaticseries.lib.util

import com.ticxo.modelengine.api.entity.Dummy
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashSet

abstract class AbstractAudience {

    private val _forceViewedTo = HashSet<UUID>()
    private val _forceHiddenTo = HashSet<UUID>()

    abstract val currentlyViewing: Set<UUID>

    val forceViewing: Set<UUID>
        get() = _forceViewedTo
    val forceHidden: Set<UUID>
        get() = _forceHiddenTo

    val addedForceView = HashSet<UUID>()
    val addedForceHide = HashSet<UUID>()

    val removedForceView = HashSet<UUID>()
    val removedForceHide = HashSet<UUID>()

    open fun apply(dummy: Dummy<*>) {

        removedForceView.forEach { uuid ->
            dummy.setForceViewing(Bukkit.getPlayer(uuid), false)
            _forceViewedTo.remove(uuid)
            addedForceView.remove(uuid)
        }
        removedForceView.clear()

        removedForceHide.forEach { uuid ->
            dummy.setForceHidden(Bukkit.getPlayer(uuid), false)
            _forceHiddenTo.remove(uuid)
            addedForceHide.remove(uuid)
        }
        removedForceHide.clear()

        addedForceView.forEach { uuid ->
            dummy.setForceViewing(Bukkit.getPlayer(uuid), true)
            _forceViewedTo.add(uuid)
        }
        addedForceView.clear()

        addedForceHide.forEach { uuid ->
            dummy.setForceHidden(Bukkit.getPlayer(uuid), true)
            _forceHiddenTo.add(uuid)
        }
        addedForceHide.clear()
    }

    class PrivateAudience: AbstractAudience() {
        override val currentlyViewing: Set<UUID>
            get() = forceViewing

        override fun apply(dummy: Dummy<*>) {
            dummy.isDetectingPlayers = false
            super.apply(dummy)
        }
    }

    class SinglePlayerAudience(val player: Player): AbstractAudience() {
        override val currentlyViewing: Set<UUID>
            get() = mutableSetOf(player.uniqueId).apply {
                this.addAll(forceViewing)
            }

        override fun apply(dummy: Dummy<*>) {
            dummy.isDetectingPlayers = false
            dummy.setForceViewing(Bukkit.getPlayer(player.uniqueId), true)
        }
    }

    class GlobalAudience: AbstractAudience() {
        override val currentlyViewing: Set<UUID>
            get() = Bukkit.getOnlinePlayers().map { it.uniqueId }.toMutableSet().apply {
                this.removeAll(forceHidden)
            }
    }

}