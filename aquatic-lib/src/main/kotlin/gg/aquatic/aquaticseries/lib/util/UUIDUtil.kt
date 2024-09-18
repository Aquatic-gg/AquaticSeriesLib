package gg.aquatic.aquaticseries.lib.util

import java.nio.ByteBuffer
import java.util.*


fun UUID.toBytes(): ByteArray {
    return ByteBuffer.allocate(16).putLong(this.mostSignificantBits).putLong(this.leastSignificantBits).array()
}

fun ByteArray.toUUID(): UUID {
    require(this.size >= 2) { "Byte array too small." }
    val bb: ByteBuffer = ByteBuffer.wrap(this)
    return UUID(bb.getLong(), bb.getLong())
}