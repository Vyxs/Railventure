package fr.manigames.railventure.api.core

import fr.manigames.railventure.api.graphics.display.Ratio

object Metric {

    val GAME_WIDTH = Ratio.R_1920_1080.width
    val GAME_HEIGHT = Ratio.R_1920_1080.height

    const val TILE_SIZE = 16f
    const val CAMERA_VIEWPORT_WIDTH = 16 * TILE_SIZE
    const val CAMERA_VIEWPORT_HEIGHT = 9 * TILE_SIZE

    // OrthographicCamera
    const val CAMERA_ZOOM = 0.5f
    const val CAMERA_ZOOM_MIN = 0.1f
    const val CAMERA_ZOOM_MAX = 1f
    // PerspectiveCamera
    const val CAMERA_HEIGHT_MIN = 40f
    const val CAMERA_HEIGHT_MAX = 300f

    const val PHYSIC_FRICTION = 0.5f
    const val PHYSIC_PLAYER_ACCELERATION = 1f
    const val PHYSIC_MIN_DELTA = 0.001f

    const val MAP_CHUNK_SIZE = 16
    const val MAP_TILE_LAYER = 4
}