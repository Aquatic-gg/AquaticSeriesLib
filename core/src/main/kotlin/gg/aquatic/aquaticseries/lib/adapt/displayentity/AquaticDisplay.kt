package gg.aquatic.aquaticseries.lib.adapt.displayentity

import org.bukkit.Color
import org.bukkit.entity.Entity
import org.joml.AxisAngle4f
import org.joml.Quaternionf
import org.joml.Vector3f

interface AquaticDisplay {

    fun despawn()

    val isPacket: Boolean
    fun getEntity(): Entity

    fun getTransformation(): Transformation

    fun setTransformation(var1: Transformation)

    fun getInterpolationDuration(): Int

    fun setInterpolationDuration(var1: Int)

    fun getTeleportDuration(): Int

    fun setTeleportDuration(var1: Int)

    fun getViewRange(): Float

    fun setViewRange(var1: Float)

    fun getShadowRadius(): Float

    fun setShadowRadius(var1: Float)

    fun getShadowStrength(): Float

    fun setShadowStrength(var1: Float)

    fun getDisplayWidth(): Float

    fun setDisplayWidth(var1: Float)

    fun getDisplayHeight(): Float

    fun setDisplayHeight(var1: Float)

    fun getInterpolationDelay(): Int

    fun setInterpolationDelay(var1: Int)

    fun getBillboard(): Billboard

    fun setBillboard(billboard: Billboard)

    fun getGlowColorOverride(): Color?

    fun setGlowColorOverride(var1: Color?)

    fun getBrightness(): Brigthness?

    fun setBrightness(brightness: Brigthness)

    class Transformation constructor(
        val translation: Vector3f,
        val leftRotation: Quaternionf,
        val scale: Vector3f,
        val rightRotation: Quaternionf,
    ) {

        constructor(
            translation: Vector3f,
            leftRotation: AxisAngle4f,
            scale: Vector3f,
            rightRotation: AxisAngle4f,
        ): this(
            translation,
            Quaternionf(leftRotation),
            scale,
            Quaternionf(rightRotation)
        )

    }

    class Brigthness(
        val blockLight: Int,
        val skyLight: Int,
    )

    enum class Billboard {
        FIXED,
        VERTICAL,
        HORIZONTAL,
        CENTER
    }
}