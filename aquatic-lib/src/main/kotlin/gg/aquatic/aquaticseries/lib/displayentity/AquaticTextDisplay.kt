package gg.aquatic.aquaticseries.lib.displayentity

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.Color

interface AquaticTextDisplay: AquaticDisplay {

    fun getText(): AquaticString?

    fun setText(var1: AquaticString?)

    fun getLineWidth(): Int

    fun setLineWidth(var1: Int)

    @Deprecated("")
    fun getBackgroundColor(): Color?

    @Deprecated("")
    fun setBackgroundColor(var1: Color?)

    fun getTextOpacity(): Byte

    fun setTextOpacity(var1: Byte)

    fun isShadowed(): Boolean

    fun setShadowed(var1: Boolean)

    fun isSeeThrough(): Boolean

    fun setSeeThrough(var1: Boolean)

    fun isDefaultBackground(): Boolean

    fun setDefaultBackground(var1: Boolean)

    fun getAlignment(): TextAlignment

    fun setAlignment(var1: TextAlignment)

    enum class TextAlignment {
        CENTER,
        LEFT,
        RIGHT
    }

}