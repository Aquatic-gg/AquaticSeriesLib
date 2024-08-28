package gg.aquatic.aquaticseries.lib.displayentity

import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticBlockDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticItemDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticTextDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.IDisplayEntityAdapter
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.audience.BlacklistAudience
import gg.aquatic.aquaticseries.lib.displayentity.impl.AquaticTextDisplayImpl
import org.bukkit.Location

object DisplayEntityAdapter: IDisplayEntityAdapter {
    override fun createTextDisplay(location: Location): AquaticTextDisplay {
        return AquaticTextDisplayImpl(false, location,BlacklistAudience(mutableListOf()))
    }

    override fun createPacketTextDisplay(location: Location, audienceList: AquaticAudience): AquaticTextDisplay {
        return AquaticTextDisplayImpl(true, location, audienceList)
    }

    override fun createItemDisplay(location: Location): AquaticItemDisplay {
        TODO("Not yet implemented")
    }

    override fun createPacketItemDisplay(location: Location, audienceList: AquaticAudience): AquaticItemDisplay {
        TODO("Not yet implemented")
    }

    override fun createBlockDisplay(location: Location): AquaticBlockDisplay {
        TODO("Not yet implemented")
    }

    override fun createPacketBlockDisplay(location: Location, audienceList: AquaticAudience): AquaticBlockDisplay {
        TODO("Not yet implemented")
    }
}