package gg.aquatic.aquaticseries.lib.util

import org.bukkit.block.BlockFace
import org.bukkit.block.ShulkerBox
import org.bukkit.block.data.Directional

fun ShulkerBox.setFace(face: BlockFace) {
    val directional = this as Directional
    directional.facing = face
}

fun ShulkerBox.getFace(): BlockFace {
    val directional = this as Directional
    return directional.facing
}