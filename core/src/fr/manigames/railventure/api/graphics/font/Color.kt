package fr.manigames.railventure.api.graphics.font

data class Color(
    val r: Float,
    val g: Float,
    val b: Float,
    val a: Float
)

object Colors {
    val WHITE = Color(1f, 1f, 1f, 1f)
    val BLACK = Color(0f, 0f, 0f, 1f)
    val RED = Color(1f, 0f, 0f, 1f)
    val GREEN = Color(0f, 1f, 0f, 1f)
    val BLUE = Color(0f, 0f, 1f, 1f)
    val YELLOW = Color(1f, 1f, 0f, 1f)
    val MAGENTA = Color(1f, 0f, 1f, 1f)
    val CYAN = Color(0f, 1f, 1f, 1f)
}