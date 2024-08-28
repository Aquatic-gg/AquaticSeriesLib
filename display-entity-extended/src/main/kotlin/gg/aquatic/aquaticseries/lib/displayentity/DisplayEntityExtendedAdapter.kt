package gg.aquatic.aquaticseries.lib.displayentity

import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticBlockDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticItemDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticTextDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.IDisplayEntityAdapter
import gg.aquatic.aquaticseries.lib.displayentity.implextended.AquaticTextDisplayImpl
import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.Location

object DisplayEntityExtendedAdapter: IDisplayEntityAdapter {
    override fun createTextDisplay(location: Location): AquaticTextDisplay {
        return AquaticTextDisplayImpl(false, location, AudienceList(mutableListOf(),AudienceList.Mode.WHITELIST))
    }

    override fun createPacketTextDisplay(location: Location, audienceList: AudienceList): AquaticTextDisplay {
        return AquaticTextDisplayImpl(true, location, audienceList)
    }

    override fun createItemDisplay(location: Location): AquaticItemDisplay {
        TODO("Not yet implemented")
    }

    override fun createPacketItemDisplay(location: Location, audienceList: AudienceList): AquaticItemDisplay {
        TODO("Not yet implemented")
    }

    override fun createBlockDisplay(location: Location): AquaticBlockDisplay {
        TODO("Not yet implemented")
    }

    override fun createPacketBlockDisplay(location: Location, audienceList: AudienceList): AquaticBlockDisplay {
        TODO("Not yet implemented")
    }
}