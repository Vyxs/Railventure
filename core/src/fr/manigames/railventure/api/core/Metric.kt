package fr.manigames.railventure.api.core

import fr.manigames.railventure.api.graphics.display.Ratio

object Metric {

    val GAME_WIDTH = Ratio.R_1920_1080.width
    val GAME_HEIGHT = Ratio.R_1920_1080.height

    // Never change this value
    private const val NATIVE_TILE_SIZE = 16f

    const val TILE_SIZE = 16f

    /**
     * PPM (pixel per meter).
     * Used to keep the same view, speed, etc... when changing the tile size.
     **/
    private const val PPM = TILE_SIZE / NATIVE_TILE_SIZE

    const val CAMERA_VIEWPORT_WIDTH = 16 * TILE_SIZE
    const val CAMERA_VIEWPORT_HEIGHT = 9 * TILE_SIZE

    // OrthographicCamera
    const val CAMERA_ZOOM = 0.5f * PPM
    const val CAMERA_ZOOM_MIN = 0.1f * PPM
    const val CAMERA_ZOOM_MAX = 1f * PPM
    const val CAMERA_DEBUG_ZOOM_ACCELERATION = 0.05f * PPM
    // PerspectiveCamera
    const val CAMERA_HEIGHT = 260f * PPM
    const val CAMERA_HEIGHT_MIN = 40f * PPM
    const val CAMERA_HEIGHT_MAX = 300f * PPM
    const val CAMERA_DEBUG_HEIGHT_ACCELERATION = 3f * PPM

    const val CAMERA_DEBUG_SPEED = 10f * PPM

    const val PHYSIC_FRICTION = 0.5f
    const val PHYSIC_PLAYER_ACCELERATION = 1f
    const val PHYSIC_MIN_DELTA = 0.001f

    const val MAP_CHUNK_SIZE = 16
    const val MAP_TILE_LAYER = 4
}