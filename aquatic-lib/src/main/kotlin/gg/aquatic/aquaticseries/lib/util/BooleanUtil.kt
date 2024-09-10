package gg.aquatic.aquaticseries.lib.util

infix fun <T>Boolean.thenOrNull(value: T): T? {
    return if (this) value else null
}

infix fun <T>Boolean.then(value: T): ThenOr<T> {
    return ThenOr(value, this)
}

data class ThenOr<T>(val value: T, val result: Boolean) {
    infix fun or(value: T): T {
        return if (result) this.value else value
    }
}