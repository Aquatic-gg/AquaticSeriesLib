package gg.aquatic.aquaticseries.interactable

data class InteractableData(
    val id: String,
    val yaw: Float,
    val pitch: Float,
    val data: String,
    val previousShape: MutableMap<Int, MutableMap<Int, String>>,
    val nullChars: MutableList<Char>
)