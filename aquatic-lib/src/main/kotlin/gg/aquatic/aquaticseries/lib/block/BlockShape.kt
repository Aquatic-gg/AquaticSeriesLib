package gg.aquatic.aquaticseries.lib.block


class BlockShape(
    val layers: MutableMap<Int, MutableMap<Int, String>>,
    val blocks: MutableMap<Char, AquaticBlock>
) {
}