package gg.aquatic.aquaticseries.lib.network

import kotlinx.serialization.Serializable

@Serializable
class NetworkResponse(
    val status: Status,
    val message: String?,
) {


    enum class Status {
        SUCCESS,
        ERROR,
    }

}