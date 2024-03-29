package fr.manigames.railventure.api.map.base

/**
 * Map of the game. It contains chunks of tiles. The map is a 2D array of chunks.
 * Each chunk is a 2D array of tiles.
 *
 * @param V The type of the tile
 * @param S The type of the tileStack
 **/
interface Map<V, S> {

    /**
     * Get the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return The chunk at the given position
     */
    fun getChunk(x: Int, y: Int): MapChunk<V, S>?

    /**
     * Set the chunk.
     *
     * @param chunk The chunk to set
     */
    fun setChunk(chunk: MapChunk<V, S>)

    /**
     * Check if the chunk at the given position is loaded
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return True if the chunk is loaded, false otherwise
     */
    fun hasChunk(x: Int, y: Int): Boolean

    /**
     * Get the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @return The tile at the given position
     */
    fun getTile(tileX: Int, tileY: Int, tileZ: Int): V?

    /**
     * Set the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param tileType The tile type to set
     */
    fun setTile(tileX: Int, tileY: Int, tileZ: Int, tileType: V) : Boolean
}