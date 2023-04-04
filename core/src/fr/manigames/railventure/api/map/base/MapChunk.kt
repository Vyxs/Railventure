package fr.manigames.railventure.api.map.base

/**
 * A chunk of the map. It contains a 2D array of tiles
 */
interface MapChunk<V> {

    /**
     * Get the x position of the chunk
     *
     * @return The x position of the chunk
     */
    fun getChunkX(): Int

    /**
     * Get the y position of the chunk
     *
     * @return The y position of the chunk
     */
    fun getChunkY(): Int

    /**
     * Get the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param z The z position of the tile (layer)
     * @return The tile at the given position
     */
    fun getTile(x: Int, y: Int, z: Int): V

    /**
     * Set the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param z The z position of the tile (layer)
     * @param tileType The tile type to set
     */
    fun setTile(x: Int, y: Int, z: Int, tileType: V)

    /**
     * Get the tiles of the chunk
     *
     * @return The tiles of the chunk
     */
    fun getTiles(): Array<Array<TileLayer>>

    /**
     * Set the tiles of the chunk
     *
     * @param tiles The tiles of the chunk
     */
    fun setTiles(tiles: Array<Array<TileLayer>>)

    /**
     * Get the world position of the tile
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @return The position of the chunk
     */
    fun getTileWorldPosition(x: Int, y: Int): Pair<Int, Int>

}