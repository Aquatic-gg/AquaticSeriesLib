package gg.aquatic.aquaticseries.lib.requirement

class ConfiguredRequirement<T>(
    val requirement: AbstractRequirement<T>,
    val arguments: Map<String, Any?>
) {

    fun check(binder: T): Boolean {
        var negate = false
        if (arguments.containsKey("negate")) {
            negate = arguments["negate"] as Boolean
        }
        val value: Boolean = requirement.check(binder, arguments)
        return if (negate) {
            !value
        } else {
            value
        }
    }

}