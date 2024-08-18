package gg.aquatic.aquaticseries.lib.requirement

object RequirementTypes {

    val requirementTypes = HashMap<String, AbstractRequirement<*>>()

    fun register(id: String, requirement: AbstractRequirement<*>) {
        requirementTypes[id] = requirement
    }

    fun unregister(id: String) {
        requirementTypes -= id
    }

    operator fun get(id: String): AbstractRequirement<*>? {
        return requirementTypes[id]
    }
}