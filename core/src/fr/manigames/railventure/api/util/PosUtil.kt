package fr.manigames.railventure.api.util

import fr.manigames.railventure.api.core.Metric
import kotlin.math.*

object PosUtil {

    /**
     * Returns world position using window coordinates.
     *
     * @param x Chunk X coordinate
     * @param y Chunk Y coordinate
     * @return World position
     **/
    fun getWorldPosition(x: Float, y: Float): Pair<Float, Float> {
        return Pair(x / Metric.TILE_SIZE, y / Metric.TILE_SIZE)
    }

    fun getXorYWorldPosition(xory: Float): Float {
        return xory / Metric.TILE_SIZE
    }

    /**
     * Returns chunk position using world coordinates.
     *
     * example: -0.1, 12 -> -1, 0
     *         5, 14 -> 0, 0
     *         18, -2 -> 1, -1
     *
     * The y coordinate is inverted. If you are in the chunk (x0, y0) and you want to go up, you have to go to (x0, y-1)
     *
     * @param worldX World X coordinate
     * @param worldY World Y coordinate
     * @return Chunk position
     **/
    fun getChunkPosition(worldX: Float, worldY: Float): Pair<Int, Int> {
        val chunkSize = Metric.MAP_CHUNK_SIZE

        val chunkX = if (worldX < 0) {
            (worldX / chunkSize).toInt() - 1
        } else {
            (worldX / chunkSize).toInt()
        }
        val chunkY = if (worldY < 0) {
            (worldY / chunkSize).toInt() - 1
        } else {
            (worldY / chunkSize).toInt()
        }
        return Pair(chunkX, chunkY)
    }

    fun getXorYChunkPosition(xory: Float): Int {
        val chunkSize = Metric.MAP_CHUNK_SIZE

        return if (xory < 0) {
            (xory / chunkSize).toInt() - 1
        } else {
            (xory / chunkSize).toInt()
        }
    }

    /**
     * Returns tile position using native coordinates.
     * zoom is between 0 and 1
     **/
    fun getChunkVisibleHorizontal(x: Float, viewportWidth: Float, zoom: Float): Int {
        val chunkSize = Metric.MAP_CHUNK_SIZE * Metric.TILE_SIZE / zoom
        val halfViewportWidth = viewportWidth / 2
        val leftChunk = floor((x - halfViewportWidth).toDouble() / chunkSize).toInt()
        val rightChunk = ceil((x + halfViewportWidth).toDouble() / chunkSize).toInt()
        return rightChunk - leftChunk
    }

    /**
     * Returns tile position using native coordinates.
     * zoom is between 0 and 1
     **/
    fun getChunkVisibleVertical(y: Float, viewportHeight: Float, zoom: Float): Int {
        val chunkSize = Metric.MAP_CHUNK_SIZE * Metric.TILE_SIZE / zoom
        val halfViewportHeight = viewportHeight / 2
        val topChunk = floor((y - halfViewportHeight).toDouble() / chunkSize).toInt()
        val bottomChunk = ceil((y + halfViewportHeight).toDouble() / chunkSize).toInt()
        return bottomChunk - topChunk
    }

    /**
     * Return how many chunks are visible in the viewport. Generally used to know how many chunks to load using the camera position.
     *
     * Using the following settings :
     *
     * Camera viewport -> (1920, 1080)
     * Tile size -> 16
     * Chunk size -> 16
     *
     * The number of chunk visible cant be more than 54.
     *
     * @param x native x coordinate
     * @param y native y coordinate
     * @param viewportWidth viewport width
     * @param viewportHeight viewport height
     * @return number of visible chunks
     **/
    fun getChunkVisible(x: Int, y: Int, viewportWidth: Float, viewportHeight: Float, zoom: Float): Int {
        val chunkSize = Metric.MAP_CHUNK_SIZE * Metric.TILE_SIZE / zoom
        val halfViewportWidth = viewportWidth / 2
        val halfViewportHeight = viewportHeight / 2
        val leftChunk = floor((x - halfViewportWidth).toDouble() / chunkSize).toInt()
        val topChunk = floor((y - halfViewportHeight).toDouble() / chunkSize).toInt()
        val rightChunk = ceil((x + halfViewportWidth).toDouble() / chunkSize).toInt()
        val bottomChunk = ceil((y + halfViewportHeight).toDouble() / chunkSize).toInt()
        return (rightChunk - leftChunk) * (bottomChunk - topChunk)
    }
}