package gg.aquatic.aquaticseries.lib.block.impl

import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.Location
import org.bukkit.block.data.BlockData

class VanillaBlock(
    override val blockData: BlockData
): AquaticBlock() {
    override fun place(location: Location) {
        location.block.type = blockData.material
        location.block.blockData = blockData
    }

    override fun placePacket(location: Location, audienceList: AudienceList): PacketBlock {
        return PacketBlock(location, blockData, audienceList) {}
    }
}