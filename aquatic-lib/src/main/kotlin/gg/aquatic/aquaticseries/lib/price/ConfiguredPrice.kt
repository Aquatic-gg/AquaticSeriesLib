package gg.aquatic.aquaticseries.lib.price

class ConfiguredPrice<T>(
    val price: AbstractPrice<T>,
    val arguments: Map<String,Any?>
) {

    fun take(binder: T) {
        price.take(binder, arguments)
    }
    fun give(binder: T) {
        price.take(binder, arguments)
    }
    fun set(binder: T) {
        price.take(binder, arguments)
    }
    fun has(binder: T): Boolean {
        return price.has(binder, arguments)
    }

}