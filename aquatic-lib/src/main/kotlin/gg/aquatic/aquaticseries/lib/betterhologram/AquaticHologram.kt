package gg.aquatic.aquaticseries.lib.betterhologram

import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.TreeMap
import java.util.UUID
import java.util.function.Function

class AquaticHologram(
    var filter: Function<Player,Boolean>,
    val failHologram: AquaticHologram?,
    val lines: MutableList<Line>,
    var anchor: Anchor,
    location: Location,
    val viewRange: Double,
) {

    var despawned = false
        private set
    private var _location: Location = location.clone()

    fun move(location: Location) {
        _location = location.clone()
        for (line in lines) {
            line.move(location)
        }
        failHologram?.move(location)
    }

    val seenBy: MutableSet<UUID> = mutableSetOf()
    val playersInRange: MutableSet<UUID> = mutableSetOf()

    private var tick = 4

    fun update() {
        _location.world ?: return
        tickRange()
        for (player in playersInRange.mapNotNull { Bukkit.getPlayer(it) }) {
            val canSee = canBeSeenBy(player) ?: continue
            val lines = canSee.lines.mapNotNull { it.canBeSeenBy(player) }

            if (!canSee.seenBy.contains(player.uniqueId)) {
                canSee.seenBy.add(player.uniqueId)
            }

            val totalHeight = lines.sumOf { it.height }
            var currentHeight = 0.0
            for ((i,line) in lines.reversed().withIndex()) {
                val offset = when (canSee.anchor) {
                    Anchor.BOTTOM -> {
                        val vector = Vector(0.0, currentHeight, 0.0)
                        currentHeight += line.height
                        vector
                    }

                    Anchor.MIDDLE -> {
                        currentHeight += line.height
                        val yOffset = (currentHeight - (totalHeight / 2.0))
                        Vector(0.0, yOffset , 0.0)
                    }

                    Anchor.TOP -> {
                        currentHeight += line.height
                        Vector(0.0, totalHeight - currentHeight, 0.0)
                    }
                }

                line.showOrUpdate(player, canSee._location, offset)
            }
        }
        tick()
    }

    private fun tick() {
        for (line in lines) {
            line.tick()
        }
        failHologram?.tick()
    }

    private fun tickRange() {
        tick++
        if (tick >= 4) {
            tick = 0
            val remaining = mutableSetOf(*playersInRange.toTypedArray())
            val world = _location.world ?: return
            val range = viewRange*viewRange
            for (player in world.players) {
                if (player.location.distanceSquared(_location) <= range) {
                    remaining.remove(player.uniqueId)
                    if (playersInRange.contains(player.uniqueId)) continue
                    playersInRange.add(player.uniqueId)
                    continue
                }
                if (playersInRange.contains(player.uniqueId)) {
                    playersInRange.remove(player.uniqueId)
                    hideAll(player)
                    remaining.remove(player.uniqueId)
                }
            }
            for (uuid in remaining) {
                val player = Bukkit.getPlayer(uuid)
                playersInRange.remove(uuid)
                if (player == null) {
                    removeFromCacheAll(uuid)
                    continue
                }
                hideAll(player)
            }
        }
    }

    private fun hideAll(player: Player) {
        for (line in lines) {
            line.hideAll(player)
        }
        failHologram?.hideAll(player)
        removeFromCacheAll(player.uniqueId)
    }

    private fun removeFromCacheAll(uuid: UUID) {
        for (line in lines) {
            line.removeFromCacheAll(uuid)
        }
        failHologram?.removeFromCacheAll(uuid)
    }

    fun despawn() {
        for (line in lines) {
            for (player in Bukkit.getOnlinePlayers()) {
                hideAll(player)
            }
        }
        despawned = true
        failHologram?.despawn()
    }

    private fun canBeSeenBy(player: Player): AquaticHologram? {
        if (filter.apply(player)) {
            failHologram?.hide(player)
            return this
        }
        hide(player)
        return failHologram?.canBeSeenBy(player)
    }

    private fun hide(player: Player) {
        if (seenBy.contains(player.uniqueId)) {
            seenBy.remove(player.uniqueId)
            handleHide(player)
        }
    }

    private fun handleHide(player: Player) {
        for (line in lines) {
            line.hideAll(player)
        }
    }

    abstract class Line: Cloneable {

        abstract val filter: Function<Player,Boolean>
        abstract val failLine: Line?
        abstract val keyFrames: TreeMap<Int, out LineKeyframe>

        var tick = 0
        val seenBy: HashMap<UUID, Vector> = hashMapOf()

        abstract fun tick()

        fun canBeSeenBy(player: Player): Line? {
            if (filter.apply(player)) {
                failLine?.hide(player)
                return this
            }
            hide(player)
            return failLine?.canBeSeenBy(player)
        }

        fun removeFromCacheAll(uuid: UUID) {
            seenBy.remove(uuid)
            removeCacheExtra(uuid)
            failLine?.removeFromCacheAll(uuid)
        }

        protected abstract fun removeCacheExtra(uuid: UUID)

        fun showOrUpdate(player: Player, location: Location, offset: Vector) {
            show(player, location, offset)
        }

        fun hideAll(player: Player) {
            hide(player)
            failLine?.hideAll(player)
        }

        private fun hide(player: Player) {
            if (seenBy.contains(player.uniqueId)) {
                seenBy.remove(player.uniqueId)
                removeCacheExtra(player.uniqueId)
                handleHide(player)
            }
        }

        private fun show(player: Player, location: Location, offset: Vector) {
            if (!seenBy.contains(player.uniqueId)) {
                seenBy[player.uniqueId] = offset
                handleShow(player, location, offset)
            } else {
                handleUpdate(player, location, offset)
            }
        }

        abstract val height: Double

        protected abstract fun handleHide(player: Player)
        protected abstract fun handleShow(
            player: Player,
            location: Location,
            offset: Vector
        )

        protected abstract fun handleUpdate(
            player: Player,
            location: Location,
            offset: Vector
        )

        fun move(location: Location) {
            handleMove(location)
            failLine?.move(location)
        }

        protected abstract fun handleMove(location: Location)

        public abstract override fun clone(): Line
    }

    abstract class LineKeyframe {
    }

    enum class Anchor {
        TOP,
        BOTTOM,
        MIDDLE,
    }

}