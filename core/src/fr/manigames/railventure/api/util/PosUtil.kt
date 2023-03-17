package fr.manigames.railventure.api.util

import fr.manigames.railventure.api.core.Metric

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

    /**
     * Returns chunk position using world coordinates.
     *
     * example: -0.1, 12 -> -1, 0
     *         5, 14 -> 0, 0
     *         18, -2 -> 1, -1
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
}