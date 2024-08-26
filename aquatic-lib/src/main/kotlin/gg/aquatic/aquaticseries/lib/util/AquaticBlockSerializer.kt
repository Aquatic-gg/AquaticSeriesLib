package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import gg.aquatic.aquaticseries.lib.block.impl.ItemsAdderBlock
import gg.aquatic.aquaticseries.lib.block.impl.OraxenBlock
import gg.aquatic.aquaticseries.lib.block.impl.VanillaBlock
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Bisected
import org.bukkit.block.data.Directional
import org.bukkit.block.data.MultipleFacing
import org.bukkit.block.data.Openable
import org.bukkit.block.data.Powerable
import org.bukkit.block.data.Waterlogged
import org.bukkit.block.data.type.Slab
import org.bukkit.block.data.type.Stairs
import org.bukkit.configuration.ConfigurationSection

object AquaticBlockSerializer {

    fun load(section: ConfigurationSection): AquaticBlock {
        val material = section.getString("material", "STONE")!!.uppercase()
        return if (material.startsWith("ORAXEN:")) {
            OraxenBlock(material.substringAfter("ORAXEN:"))
        } else if (material.startsWith("IA:")) {
            ItemsAdderBlock(material.substringAfter("IA:"))
        } else {
            val mat = Material.valueOf(material)
            val blockData = mat.createBlockData()
            if (blockData is Directional) {
                val face = BlockFace.valueOf(section.getString("face", "NORTH")!!.uppercase())
                blockData.facing = face
            }
            if (blockData is Openable) {
                val open = section.getBoolean("opened", false)
                blockData.isOpen = open
            }
            if (blockData is Powerable) {
                val powered = section.getBoolean("powered", false)
                blockData.isPowered = powered
            }
            if (blockData is Bisected) {
                val half = Bisected.Half.valueOf(section.getString("half", "BOTTOM")!!.uppercase())
                blockData.half = half
            }
            if (blockData is Waterlogged) {
                val waterlogged = section.getBoolean("waterlogged", false)
                blockData.isWaterlogged = waterlogged
            }
            if (blockData is MultipleFacing) {
                val faces = section.getStringList("faces").map { BlockFace.valueOf(it.uppercase()) }
                for (face in faces) {
                    blockData.setFace(face, true)
                }
            }
            if (blockData is Stairs) {
                val shape = Stairs.Shape.valueOf(section.getString("stairs-shape", "STRAIGHT")!!.uppercase())
                blockData.shape = shape
            }
            if (blockData is Slab) {
                val type = Slab.Type.valueOf(section.getString("slab-type", "BOTTOM")!!.uppercase())
                blockData.type = type
            }
            VanillaBlock(blockData)
        }
    }

}