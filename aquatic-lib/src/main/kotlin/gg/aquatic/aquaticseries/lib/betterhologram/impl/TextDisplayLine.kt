package gg.aquatic.aquaticseries.lib.betterhologram.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.betterhologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.Location
import org.bukkit.entity.Display
import org.bukkit.entity.Display.Billboard
import org.bukkit.entity.Player
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Quaternionf
import org.joml.Vector3f
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

class TextDisplayLine(
    override val filter: Function<Player, Boolean>,
    override val failLine: AquaticHologram.Line?,
    override val keyFrames: TreeMap<Int, TextDisplayKeyframe>,
    val textUpdater: (Player, String) -> String,
) : AquaticHologram.Line() {

    val nmsAdapter: NMSAdapter
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        }

    var currentKeyframe: TextDisplayKeyframe = keyFrames.firstEntry().value
    val states = HashMap<UUID, TextDisplayState>()

    var entityId: Int? = null
    private fun createEntity(location: Location): Int {
        return nmsAdapter.spawnEntity(location, "text_display", WhitelistAudience(mutableListOf())) {
            it as TextDisplay
            it.billboard = Display.Billboard.CENTER
        }
    }

    override fun tick() {
        failLine?.tick()
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
        nmsAdapter.despawnEntity(listOf(entityId!!), WhitelistAudience(mutableListOf(player.uniqueId)))
    }

    override fun handleShow(player: Player, location: Location, offset: Vector) {
        if (entityId == null) {
            entityId = createEntity(location)
        }
        val state = createState(player, offset.y)
        states[player.uniqueId] = state
        nmsAdapter.resendEntitySpawnPacket(player, entityId!!)
        updateEntity(player, offset, state)
    }

    override fun handleUpdate(player: Player, location: Location, offset: Vector) {
        val state = createState(player, offset.y)
        val previousState = states[player.uniqueId]
        if (previousState == null) {
            handleShow(player, location, offset)
            return
        }
        if (previousState.isSame(state)) {
            return
        }
        states[player.uniqueId] = state
        updateEntity(player, offset, state)
    }

    override fun handleMove(location: Location) {
        nmsAdapter.teleportEntity(entityId!!, location, WhitelistAudience(seenBy.keys.toMutableList()))
    }

    private fun updateEntity(player: Player, offset: Vector, state: TextDisplayState) {
        nmsAdapter.updateEntity(entityId!!, { e ->
            e as TextDisplay
            e.transformation = Transformation(
                Vector3f(offset.x.toFloat(), offset.y.toFloat(), offset.z.toFloat()),
                Quaternionf(),
                Vector3f(state.scale, state.scale, state.scale),
                Quaternionf()
            )
            AbstractAquaticSeriesLib.INSTANCE.adapter.setDisplayText(e, state.text.toAquatic())
            e.billboard = state.billboard
        }, WhitelistAudience(mutableListOf(player.uniqueId)))
    }

    private fun createState(player: Player, height: Double): TextDisplayState {
        val state = TextDisplayState(
            textUpdater(player, currentKeyframe.text.string),
            currentKeyframe.height + height,
            currentKeyframe.scale,
            currentKeyframe.billboard,
        )
        return state
    }

    class TextDisplayKeyframe(
        val text: AquaticString,
        val height: Double = 0.3,
        val scale: Float = 1.0f,
        val billboard: Billboard = Billboard.CENTER,
    ) : AquaticHologram.LineKeyframe() {

    }

    class TextDisplayState(
        val text: String,
        val height: Double,
        val scale: Float,
        val billboard: Billboard,
    ) {

        fun isSame(other: TextDisplayState): Boolean {
            return text == other.text &&
                    height == other.height &&
                    scale == other.scale &&
                    billboard == other.billboard
        }

    }
}