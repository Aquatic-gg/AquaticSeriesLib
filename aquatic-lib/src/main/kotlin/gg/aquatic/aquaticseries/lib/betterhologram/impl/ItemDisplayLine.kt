package gg.aquatic.aquaticseries.lib.betterhologram.impl

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.betterhologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.lib.util.lookAtYawPitch
import gg.aquatic.aquaticseries.lib.util.runSync
import org.bukkit.Location
import org.bukkit.entity.Display.Billboard
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Quaternionf
import org.joml.Vector3f
import java.util.*
import java.util.function.Function

class ItemDisplayLine(
    override val filter: Function<Player, Boolean>,
    override val failLine: AquaticHologram.Line?,
    override val keyFrames: TreeMap<Int, ItemDisplayKeyframe>,
) : AquaticHologram.Line() {

    val nmsAdapter: NMSAdapter
        get() {
            return AquaticSeriesLib.INSTANCE.nmsAdapter!!
        }

    var currentKeyframe: ItemDisplayKeyframe = keyFrames.firstEntry().value
    val states = HashMap<UUID, ItemDisplayState>()

    var entityId: Int? = null
    private fun createEntity(location: Location): Int {
        return nmsAdapter.spawnEntity(location, "item_display", WhitelistAudience(mutableListOf())) {
            it as ItemDisplay
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
        val keyframe: ItemDisplayKeyframe? = keyFrames.higherEntry(tick)?.value
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

    override fun handleShow(player: Player, location: Location, offset: Vector, billboard: AquaticHologram.Billboard) {
        if (entityId == null) {
            entityId = createEntity(location)
        }
        val state = createState(offset.y)
        states[player.uniqueId] = state
        nmsAdapter.resendEntitySpawnPacket(player, entityId!!)
        updateEntity(player, offset, state, billboard)
    }

    override fun handleUpdate(player: Player, location: Location, offset: Vector, billboard: AquaticHologram.Billboard) {
        val state = createState(offset.y)
        val previousState = states[player.uniqueId]
        if (previousState == null) {
            handleShow(player, location, offset, billboard)
            return
        }
        if (billboard != AquaticHologram.Billboard.LOOK_AT_PLAYER) {
            if (previousState.isSame(state)) {
                return
            }
        }
        states[player.uniqueId] = state
        updateEntity(player, offset, state, billboard)
    }

    private fun updateEntity(player: Player, offset: Vector, state: ItemDisplayState, billboard: AquaticHologram.Billboard) {
        var location: Location? = null

        nmsAdapter.updateEntity(entityId!!, { e ->
            e as ItemDisplay
            location = e.location.clone()
            e.transformation = Transformation(
                Vector3f(offset.x.toFloat(), offset.y.toFloat(), offset.z.toFloat()),
                Quaternionf(),
                Vector3f(state.scale, state.scale, state.scale),
                Quaternionf()
            )
            e.itemStack = currentKeyframe.item
            e.itemDisplayTransform = currentKeyframe.itemDisplayTransform
            when (billboard) {
                AquaticHologram.Billboard.CENTER -> {
                    e.billboard = Billboard.CENTER
                }
                AquaticHologram.Billboard.FIXED -> {
                    e.billboard = Billboard.FIXED
                }
                AquaticHologram.Billboard.LOOK_AT_PLAYER -> {
                    e.billboard = Billboard.FIXED

                    val location = e.location.clone()
                    val (yaw, pitch) = location.lookAtYawPitch(player.eyeLocation)
                    location.yaw = yaw
                    location.pitch = pitch
                    runSync {
                        nmsAdapter.teleportEntity(
                            e.entityId,
                            location,
                            WhitelistAudience(mutableListOf(player.uniqueId))
                        )
                    }
                }
            }
        }, WhitelistAudience(mutableListOf(player.uniqueId)))

        if (billboard == AquaticHologram.Billboard.LOOK_AT_PLAYER && location != null) {
            val (yaw, pitch) = location!!.lookAtYawPitch(player.eyeLocation)
            location!!.yaw = yaw
            location!!.pitch = pitch

            nmsAdapter.updateEntity(entityId!!, { e ->
                e as ItemDisplay
                try {
                    e.teleportDuration = 2
                } catch (e: Exception) {

                }
            }, WhitelistAudience(mutableListOf(player.uniqueId)))

            runSync {
                nmsAdapter.teleportEntity(
                    entityId!!,
                    location!!,
                    WhitelistAudience(mutableListOf(player.uniqueId))
                )
            }
        }
    }

    private fun createState(height: Double): ItemDisplayState {
        val state = ItemDisplayState(
            currentKeyframe.item,
            currentKeyframe.height + height,
            currentKeyframe.scale,
            currentKeyframe.billboard,
            currentKeyframe.itemDisplayTransform,
        )
        return state
    }

    class ItemDisplayKeyframe(
        val item: ItemStack,
        val height: Double = 0.3,
        val scale: Float = 1.0f,
        val billboard: Billboard = Billboard.CENTER,
        val itemDisplayTransform: ItemDisplayTransform
    ) : AquaticHologram.LineKeyframe() {

    }

    override fun clone(): ItemDisplayLine {
        return ItemDisplayLine(filter, failLine?.clone() , TreeMap(keyFrames))
    }

    override fun handleMove(location: Location) {
        nmsAdapter.teleportEntity(entityId!!, location, WhitelistAudience(seenBy.keys.toMutableList()))
    }

    class ItemDisplayState(
        val item: ItemStack,
        val height: Double,
        val scale: Float,
        val billboard: Billboard,
        val itemDisplayTransform: ItemDisplayTransform
    ) {

        fun isSame(other: ItemDisplayState): Boolean {
            return item.isSimilar(other.item) &&
                    height == other.height &&
                    scale == other.scale &&
                    billboard == other.billboard &&
                    itemDisplayTransform == other.itemDisplayTransform
        }

    }
}