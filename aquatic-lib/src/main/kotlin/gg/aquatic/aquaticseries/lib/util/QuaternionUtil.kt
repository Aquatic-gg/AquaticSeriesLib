package gg.aquatic.aquaticseries.lib.util

import org.joml.Quaternionf

fun yawPitchToQuaternion(yaw: Float, pitch: Float): Quaternionf {
    // Convert degrees to radians
    val yawRad = Math.toRadians(yaw.toDouble()).toFloat()
    val pitchRad = Math.toRadians(pitch.toDouble()).toFloat()

    // Create quaternions for yaw and pitch
    val quaternionYaw = Quaternionf().rotateY(yawRad)
    val quaternionPitch = Quaternionf().rotateX(pitchRad)

    // Combine the two quaternions
    return quaternionYaw.mul(quaternionPitch)
}