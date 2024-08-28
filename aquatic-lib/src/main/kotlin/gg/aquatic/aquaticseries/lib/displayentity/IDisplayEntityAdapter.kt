package gg.aquatic.aquaticseries.lib.displayentity

import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import org.bukkit.Location

interface IDisplayEntityAdapter {

    fun createTextDisplay(location: Location): AquaticTextDisplay
    fun createPacketTextDisplay(location: Location, audienceList: AquaticAudience): AquaticPacketDisplay
    fun createItemDisplay(location: Location): AquaticItemDisplay
    fun createPacketItemDisplay(location: Location, audienceList: AquaticAudience): AquaticPacketDisplay
    fun createBlockDisplay(location: Location): AquaticBlockDisplay
    fun createPacketBlockDisplay(location: Location, audienceList: AquaticAudience): AquaticPacketDisplay

}