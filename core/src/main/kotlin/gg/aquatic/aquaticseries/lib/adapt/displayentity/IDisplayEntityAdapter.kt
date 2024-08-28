package gg.aquatic.aquaticseries.lib.adapt.displayentity

import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import org.bukkit.Location

interface IDisplayEntityAdapter {

    fun createTextDisplay(location: Location): AquaticTextDisplay
    fun createPacketTextDisplay(location: Location, audienceList: AquaticAudience): AquaticTextDisplay
    fun createItemDisplay(location: Location): AquaticItemDisplay
    fun createPacketItemDisplay(location: Location, audienceList: AquaticAudience): AquaticItemDisplay
    fun createBlockDisplay(location: Location): AquaticBlockDisplay
    fun createPacketBlockDisplay(location: Location, audienceList: AquaticAudience): AquaticBlockDisplay

}