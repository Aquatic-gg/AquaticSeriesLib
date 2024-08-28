package gg.aquatic.aquaticseries.lib.block.impl

import dev.lone.itemsadder.api.CustomBlock
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import org.bukkit.Location
import org.bukkit.block.data.BlockData

class ItemsAdderBlock(
    val iaId: String
): AquaticBlock() {
    override fun place(location: Location) {
        val customBlock: CustomBlock = CustomBlock.getInstance(iaId) ?: return
        customBlock.place(location)
    }

    override fun placePacket(location: Location, audienceList: AquaticAudience): PacketBlock {
        val blockData: BlockData = blockData
        return PacketBlock(location, blockData, audienceList) {}.apply { spawn() }
        //player.sendBlockChange(location, blockData)
        //PacketBlock()
    }

    override val blockData: BlockData
        get() {
            val customBlock: CustomBlock = CustomBlock.getInstance(iaId)!!
            return customBlock.block.blockData
        }
}