package gg.aquatic.aquaticseries.lib.betterhologram

import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.TreeMap
import java.util.UUID

class AquaticHologram(
    var filter: (Player) -> Boolean,
    val failHologram: AquaticHologram?,
    val lines: MutableList<Line>,
    val placeholders: Placeholders,
    var anchor: Anchor,
    val location: Location,
) {

    val seenBy: MutableSet<UUID> = mutableSetOf()
    val playersInRange: MutableSet<UUID> = mutableSetOf()

    private var tick = 0

    fun update() {
        location.world ?: return
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

                line.showOrUpdate(player, canSee.placeholders, canSee.location, offset)
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
            val world = location.world ?: return
            for (player in world.players) {
                if (player.location.distanceSquared(location) <= 10*10) {
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

    private fun canBeSeenBy(player: Player): AquaticHologram? {
        if (filter(player)) {
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

    abstract class Line {

        abstract val filter: (Player) -> Boolean
        abstract val failLine: Line?
        abstract val keyFrames: TreeMap<Int, out LineKeyframe>

        var tick = 0
        val seenBy: HashMap<UUID, Vector> = hashMapOf()

        abstract fun tick()

        fun canBeSeenBy(player: Player): Line? {
            if (filter(player)) {
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

        fun showOrUpdate(player: Player, placeholders: Placeholders, location: Location, offset: Vector) {
            show(player, placeholders, location, offset)
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

        private fun show(player: Player, placeholders: Placeholders, location: Location, offset: Vector) {
            if (!seenBy.contains(player.uniqueId)) {
                seenBy[player.uniqueId] = offset
                handleShow(player, placeholders, location, offset)
            } else {
                handleUpdate(player, placeholders, location, offset)
            }
        }

        abstract val height: Double

        protected abstract fun handleHide(player: Player)
        protected abstract fun handleShow(
            player: Player,
            placeholders: Placeholders,
            location: Location,
            offset: Vector
        )

        protected abstract fun handleUpdate(
            player: Player,
            placeholders: Placeholders,
            location: Location,
            offset: Vector
        )
    }

    abstract class LineKeyframe {
        abstract val time: Int
    }

    enum class Anchor {
        TOP,
        BOTTOM,
        MIDDLE,
    }

}