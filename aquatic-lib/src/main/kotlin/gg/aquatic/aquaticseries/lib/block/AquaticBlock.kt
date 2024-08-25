package gg.aquatic.aquaticseries.lib.block

import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.interactable2.AudienceList
import org.bukkit.Location
import org.bukkit.block.data.BlockData

abstract class AquaticBlock {

    abstract fun place(location: Location)
    abstract fun placePacket(location: Location, audienceList: AudienceList): PacketBlock
    abstract val blockData: BlockData

}