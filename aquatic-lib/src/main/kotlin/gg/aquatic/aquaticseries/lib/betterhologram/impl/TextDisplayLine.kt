package gg.aquatic.aquaticseries.lib.betterhologram.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.FilterAudience
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.betterhologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Quaternionf
import org.joml.Vector3f
import java.util.*
import kotlin.collections.HashMap

class TextDisplayLine(
    override val filter: (Player) -> Boolean,
    override val failLine: AquaticHologram.Line?,
    override val keyFrames: TreeMap<Int, TextDisplayKeyframe>,
) : AquaticHologram.Line() {

    val nmsAdapter: NMSAdapter
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        }

    var currentKeyframe: TextDisplayKeyframe = keyFrames.firstEntry().value
    val states = HashMap<UUID,TextDisplayState>()

    var entityId: Int? = null
    private fun createEntity(location: Location): Int {
        return nmsAdapter.spawnEntity(location, "text_display", FilterAudience(filter)) { }
    }

    override fun tick() {
        if (keyFrames.size <= 1) {
            return
        }
        tick++
        if (tick > keyFrames.lastKey()) {
            tick = 0
        }
        val keyframe: TextDisplayKeyframe? = keyFrames.higherEntry(tick)?.value
        if (keyframe != null) {
            currentKeyframe = keyframe
        }
    }

    override fun removeCacheExtra(uuid: UUID) {
        states.remove(uuid)
    }

    override val height: Double
        get() {
            return currentKeyframe.height
        }

    override fun handleHide(player: Player) {
        if (entityId == null) return
        nmsAdapter.despawnEntity(listOf(entityId!!),WhitelistAudience(mutableListOf(player.uniqueId)))
    }

    override fun handleShow(player: Player, placeholders: Placeholders, location: Location, offset: Vector) {
        if (entityId == null) {
            entityId = createEntity(location)
        }
        val state = createState(player, placeholders, offset.y)
        states[player.uniqueId] = state
        nmsAdapter.updateEntity(entityId!!, { e->
            e as TextDisplay
            e.transformation = Transformation(
                Vector3f(offset.x.toFloat(), offset.y.toFloat(), offset.z.toFloat()),
                Quaternionf(),
                Vector3f(1f, 1f, 1f),
                Quaternionf()
            )
            e.text = state.text
        }, WhitelistAudience(mutableListOf(player.uniqueId)))
    }

    override fun handleUpdate(player: Player, placeholders: Placeholders, location: Location, offset: Vector) {
        val state = createState(player, placeholders, offset.y)
        val previousState = states[player.uniqueId]
        if (previousState == null) {
            handleShow(player, placeholders, location, offset)
            return
        }
        if (
            previousState.text == state.text &&
            previousState.height == state.height
        ) {
            return
        }
        states[player.uniqueId] = state
        nmsAdapter.updateEntity(entityId!!, { e->
            e as TextDisplay
            e.transformation = Transformation(
                Vector3f(offset.x.toFloat(), offset.y.toFloat(), offset.z.toFloat()),
                Quaternionf(),
                Vector3f(1f, 1f, 1f),
                Quaternionf()
            )
            e.text = state.text
        }, WhitelistAudience(mutableListOf(player.uniqueId)))
    }

    private fun createState(player: Player, placeholders: Placeholders, height: Double): TextDisplayState {
        val state = TextDisplayState(
            placeholders.replace(currentKeyframe.text.string),
            currentKeyframe.height+height
        )
        return state
    }

    class TextDisplayKeyframe(
        override val time: Int,
        val text: AquaticString,
        val height: Double
    ) : AquaticHologram.LineKeyframe() {

    }

    class TextDisplayState(
        val text: String,
        val height: Double,
    )
}