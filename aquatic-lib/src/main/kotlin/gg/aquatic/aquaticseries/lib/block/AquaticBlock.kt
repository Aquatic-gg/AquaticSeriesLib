package gg.aquatic.aquaticseries.lib.block

import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import org.bukkit.Location
import org.bukkit.block.data.BlockData

abstract class AquaticBlock {

    abstract fun place(location: Location)
    abstract fun placePacket(location: Location, audienceList: AquaticAudience): PacketBlock
    abstract val blockData: BlockData

}