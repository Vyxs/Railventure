package fr.manigames.railventure.api.graphics.display

object Ratio {

    data class Resolution(val width: Float, val height: Float)

    // 16:9
    val R_1024_576 = Resolution(1024f, 576f)
    val R_1280_720 = Resolution(1280f, 720f)
    val R_1366_768 = Resolution(1366f, 768f)
    val R_1600_900 = Resolution(1600f, 900f)
    val R_1920_1080 = Resolution(1920f, 1080f)
    val R_2560_1440 = Resolution(2560f, 1440f)
    val R_3840_2160 = Resolution(3840f, 2160f)

}