package gg.aquatic.aquaticseries.lib.block.impl

import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import org.bukkit.Location
import org.bukkit.block.ShulkerBox
import org.bukkit.block.data.BlockData

class VanillaBlock(
    override val blockData: BlockData,
    private val extra: Int? = null
): AquaticBlock() {
    override fun place(location: Location) {
        location.block.type = blockData.material
        location.block.blockData = blockData

        val blockState = location.block.state
        if (extra != null) {
            blockState.data.data = extra.toByte()
            blockState.update(true)
        }
    }

    override fun placePacket(location: Location, audienceList: AquaticAudience): PacketBlock {
        return PacketBlock(location, blockData, audienceList) {}
    }
}