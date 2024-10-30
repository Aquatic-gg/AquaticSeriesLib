package gg.aquatic.aquaticseries.lib.util

fun Double.decimals(decimalPlaces: Int = 0): String {
    return if (decimalPlaces == 0) {
        this.toInt().toString()
    } else {
        "%.${decimalPlaces}f".format(this)
    }
}

fun Float.decimals(decimalPlaces: Int = 0): String {
    return if (decimalPlaces == 0) {
        this.toInt().toString()
    } else {
        "%.${decimalPlaces}f".format(this)
    }
}