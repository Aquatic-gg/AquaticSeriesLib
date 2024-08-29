package gg.aquatic.aquaticseries.lib.betterhologram.impl

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.betterhologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*
import kotlin.collections.HashMap

class ArmorstandLine(
    override val filter: (Player) -> Boolean,
    override val failLine: AquaticHologram.Line?,
    override val keyFrames: TreeMap<Int, ArmorstandKeyframe>,
    val textUpdater: (Player, String) -> String,
) : AquaticHologram.Line() {

    val nmsAdapter: NMSAdapter
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        }

    var currentKeyframe: ArmorstandKeyframe = keyFrames.firstEntry().value
    val states = HashMap<UUID, ArmorstandState>()

    var entityId: Int? = null
    lateinit var location: Location
    private fun createEntity(location: Location): Int {
        this.location = location
        val newLoc = location.clone().add(0.0, -1.7,0.0)
        return nmsAdapter.spawnEntity(newLoc, "armor_stand", WhitelistAudience(mutableListOf())) {
            it as ArmorStand
            it.isVisible = false
            it.isCustomNameVisible = true
            it.isMarker = true
            it.isSmall = true
            it.isSilent = true
            it.isInvulnerable = true
        }
    }

    override fun tick() {
        if (keyFrames.size <= 1) {
            return
        }
        tick++
        if (tick > keyFrames.lastKey()) {
            tick = 0
        }
        val keyframe: ArmorstandKeyframe? = keyFrames.higherEntry(tick)?.value
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

    private fun updateEntity(player: Player, offset: Vector, state: ArmorstandState) {
        nmsAdapter.updateEntity(entityId!!, { e ->
            e as ArmorStand
            state.text.toAquatic().setEntityName(e)
        }, WhitelistAudience(mutableListOf(player.uniqueId)))
        val newLoc = location.clone().add(offset).add(0.0, -1.7,0.0)
        nmsAdapter.teleportEntity(entityId!!, newLoc, WhitelistAudience(mutableListOf(player.uniqueId)))
    }

    private fun createState(player: Player, height: Double): ArmorstandState {
        val state = ArmorstandState(
            textUpdater(player, currentKeyframe.text.string),
            currentKeyframe.height + height,
        )
        return state
    }

    class ArmorstandKeyframe(
        override val time: Int,
        val text: AquaticString,
        val height: Double = 0.3,
    ) : AquaticHologram.LineKeyframe() {

    }

    class ArmorstandState(
        val text: String,
        val height: Double,
    ) {

        fun isSame(other: ArmorstandState): Boolean {
            return text == other.text &&
                    height == other.height
        }

    }
}