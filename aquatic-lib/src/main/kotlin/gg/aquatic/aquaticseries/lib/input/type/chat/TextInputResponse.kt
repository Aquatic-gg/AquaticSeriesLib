package gg.aquatic.aquaticseries.lib.input.type.chat

class TextInputResponse(
    val response: Response,
    val message: String? = null,
    val error: String? = null,
) {

    enum class Response {
        SUCCESS,
        CANCEL,
        ERROR,
    }

}