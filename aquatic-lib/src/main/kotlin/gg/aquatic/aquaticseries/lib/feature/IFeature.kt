package gg.aquatic.aquaticseries.lib.feature

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib

interface IFeature {

    val type: Features

    fun initialize(lib: AbstractAquaticSeriesLib)

}