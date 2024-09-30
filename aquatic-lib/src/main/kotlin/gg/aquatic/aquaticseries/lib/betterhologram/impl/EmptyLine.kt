package gg.aquatic.aquaticseries.lib.betterhologram.impl

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
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
            return AquaticSeriesLib.INSTANCE.nmsAdapter!!
        }

    override fun tick() {
        failLine?.tick
    }

    override fun removeCacheExtra(uuid: UUID) {

    }

    override fun handleHide(player: Player) {
    }

    override fun handleShow(player: Player, location: Location, offset: Vector, billboard: AquaticHologram.Billboard) {
    }

    override fun handleUpdate(player: Player, location: Location, offset: Vector, billboard: AquaticHologram.Billboard) {
    }

    class EmptyKeyframe(
    ) : AquaticHologram.LineKeyframe() {

    }

    override fun clone(): EmptyLine {
        return EmptyLine(filter, failLine?.clone(), height)
    }

    override fun handleMove(location: Location) {
    }
}