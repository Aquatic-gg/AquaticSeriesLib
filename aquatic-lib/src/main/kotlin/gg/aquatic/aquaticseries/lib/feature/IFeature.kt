package gg.aquatic.aquaticseries.lib.feature

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib

interface IFeature {

    val type: Features

    fun initialize(lib: AquaticSeriesLib)

}