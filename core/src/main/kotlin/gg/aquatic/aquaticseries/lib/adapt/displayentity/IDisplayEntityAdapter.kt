package gg.aquatic.aquaticseries.lib.adapt.displayentity

import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.Location

interface IDisplayEntityAdapter {

    fun createTextDisplay(location: Location): AquaticTextDisplay
    fun createPacketTextDisplay(location: Location, audienceList: AudienceList): AquaticTextDisplay
    fun createItemDisplay(location: Location): AquaticItemDisplay
    fun createPacketItemDisplay(location: Location, audienceList: AudienceList): AquaticItemDisplay
    fun createBlockDisplay(location: Location): AquaticBlockDisplay
    fun createPacketBlockDisplay(location: Location, audienceList: AudienceList): AquaticBlockDisplay

}