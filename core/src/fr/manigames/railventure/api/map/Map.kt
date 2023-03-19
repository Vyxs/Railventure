package fr.manigames.railventure.api.map

/**
 * Map of the game. It contains chunks of tiles. The map is a 2D array of chunks.
 * Each chunk is a 2D array of tiles.
 **/
interface Map<V> {

    /**
     * Get the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return The chunk at the given position
     */
    fun getChunk(x: Int, y: Int): MapChunk<V>

    /**
     * Set the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @param chunk The chunk to set
     */
    fun setChunk(x: Int, y: Int, chunk: MapChunk<V>)

    /**
     * Check if the chunk at the given position is loaded
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return True if the chunk is loaded, false otherwise
     */
    fun isChunkLoaded(x: Int, y: Int): Boolean

    /**
     * Get the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @return The tile at the given position
     */
    fun getTile(tileX: Int, tileY: Int, tileZ: Int): V

    /**
     * Set the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param tileType The tile type to set
     */
    fun setTile(tileX: Int, tileY: Int, tileZ: Int, tileType: V)
}