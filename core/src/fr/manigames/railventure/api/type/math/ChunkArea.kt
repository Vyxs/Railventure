package fr.manigames.railventure.api.type.math

import fr.manigames.railventure.api.util.PosUtil

data class ChunkArea(
    override val x1: Int,
    override val y1: Int,
    override val x2: Int,
    override val y2: Int
) : Area<Int>(x1, y1, x2, y2) {

    private var cachedPositions: Array<Pair<Int, Int>>? = null

    /**
     * Get the chunk positions of the area.
     *
     * @return The chunk positions
     **/
    fun toChunkPositions(): Array<Pair<Int, Int>> {
        if (cachedPositions != null) {
            return cachedPositions!!
        }
        val positions = mutableListOf<Pair<Int, Int>>()
        for (i in x1..x2) {
            for (j in y1..y2) {
                positions.add(Pair(i, j))
            }
        }
        cachedPositions = positions.toTypedArray()
        return cachedPositions!!
    }

    /**
     * Check if the area contains the given chunk position.
     *
     * @param x The x coordinate of the chunk
     * @param y The y coordinate of the chunk
     * @return True if the area contains the chunk position
     **/
    fun contains(x: Int, y: Int): Boolean {
        return x in x1..y1 && y in x2..y2
    }

    /**
     * Check if the area contains the given world position.
     *
     * @param worldX The x coordinate of the world position
     * @param worldY The y coordinate of the world position
     * @return True if the area contains the world position
     **/
    fun containsTile(worldX: Float, worldY: Float): Boolean {
        val pos = PosUtil.getChunkPosition(worldX, worldY)
        return contains(pos.x, pos.y)
    }

    companion object {

        /**
         * Create a new chunk area from the given chunk position and the distance.
         * The distance is the number of chunks around the given chunk position.
         * For example, if the distance is 5, the area will be 11x11 chunks.
         *
         * @param x The x coordinate of the chunk
         * @param y The y coordinate of the chunk
         * @param distance The distance around the chunk position
         * @return The chunk area
         **/
        fun fromCenter(x: Int, y: Int, distance: Int): ChunkArea {
            return ChunkArea(x - distance, y - distance, x + distance, y + distance)
        }
    }
}