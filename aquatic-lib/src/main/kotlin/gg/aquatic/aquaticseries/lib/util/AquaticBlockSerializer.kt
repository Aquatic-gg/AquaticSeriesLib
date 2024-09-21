package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import gg.aquatic.aquaticseries.lib.block.AquaticMultiBlock
import gg.aquatic.aquaticseries.lib.block.BlockShape
import gg.aquatic.aquaticseries.lib.block.impl.ItemsAdderBlock
import gg.aquatic.aquaticseries.lib.block.impl.OraxenBlock
import gg.aquatic.aquaticseries.lib.block.impl.VanillaBlock
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.*
import org.bukkit.block.data.type.Slab
import org.bukkit.block.data.type.Stairs
import org.bukkit.configuration.ConfigurationSection

object AquaticBlockSerializer {

    fun loadMultiBlock(section: ConfigurationSection): AquaticMultiBlock {
        val layersSection = section.getConfigurationSection("layers")!!
        val ingredientsSection = section.getConfigurationSection("blocks")!!

        val ingredients = HashMap<Char, AquaticBlock>()

        for (key in ingredientsSection.getKeys(false)) {
            val block = load(ingredientsSection.getConfigurationSection(key)!!)
            ingredients[key.toCharArray().first()] = block
        }
        val layers = HashMap<Int, MutableMap<Int, String>>()
        for (key in layersSection.getKeys(false)) {
            val layer = layersSection.getConfigurationSection(key)!!
            val layerBlocks = HashMap<Int, String>()
            for (layerKey in layer.getKeys(false)) {
                layerBlocks[layerKey.toInt()] = layer.getString(layerKey)!!
            }
            layers[key.toInt()] = layerBlocks
        }
        return AquaticMultiBlock(BlockShape(layers, ingredients))
    }

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

            val extra = if (section.contains("extra")) {
                section.getInt("extra")
            } else null

            VanillaBlock(blockData, extra)
        }
    }

}