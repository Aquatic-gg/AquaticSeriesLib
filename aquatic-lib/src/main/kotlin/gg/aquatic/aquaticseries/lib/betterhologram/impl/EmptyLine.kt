package gg.aquatic.aquaticseries.lib.betterhologram.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.betterhologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*
import java.util.function.Function

class EmptyLine(
    override val filter: Function<Player,Boolean>,
    override val failLine: AquaticHologram.Line?,
    override val height: Double,
) : AquaticHologram.Line() {

    override val keyFrames: TreeMap<Int, EmptyKeyframe> = TreeMap()

    val nmsAdapter: NMSAdapter
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        }

    override fun tick() {
        failLine?.tick
    }

    override fun removeCacheExtra(uuid: UUID) {

    }

    override fun handleHide(player: Player) {
    }

    override fun handleShow(player: Player, location: Location, offset: Vector) {
    }

    override fun handleUpdate(player: Player, location: Location, offset: Vector) {
    }

    class EmptyKeyframe(
    ) : AquaticHologram.LineKeyframe() {

    }

    override fun handleMove(location: Location) {
    }
}