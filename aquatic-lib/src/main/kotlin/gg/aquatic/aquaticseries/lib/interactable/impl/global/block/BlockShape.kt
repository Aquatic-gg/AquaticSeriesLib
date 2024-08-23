package gg.aquatic.aquaticseries.lib.interactable.impl.global.block

import gg.aquatic.aquaticseries.lib.block.AquaticBlock


class BlockShape(
    val layers: MutableMap<Int, MutableMap<Int, String>>,
    val blocks: MutableMap<Char, AquaticBlock>
) {
}