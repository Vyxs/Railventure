package fr.manigames.railventure.api.map.base

/**
 * A chunk of the map. It contains a 2D array of tiles
 *
 * @param T The type of the tile
 * @param S The stack of tiles
 */
interface MapChunk<T, S> {

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
    fun getTile(x: Int, y: Int, z: Int): T

    /**
     * Get the tile stack at the given position
     *
     * @param x The x position of the tileStack
     * @param y The y position of the tileStack
     * @return The stack of tiles
     **/
    fun getTileStack(x: Int, y: Int): S

    /**
     * Set the tile at the given position
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param z The z position of the tile (layer)
     * @param tileType The tile type to set
     */
    fun setTile(x: Int, y: Int, z: Int, tileType: T)

    /**
     * Set the tile stack at the given position
     *
     * @param x The x position of the tileStack
     * @param y The y position of the tileStack
     * @param stack The stack of tiles
     **/
    fun setTileStack(x: Int, y: Int, stack: S)

    /**
     * Get the tiles of the chunk
     *
     * @return The tiles of the chunk
     */
    fun getTiles(): Array<Array<S>>

    /**
     * Set the tiles of the chunk
     *
     * @param tiles The tiles of the chunk
     */
    fun setTiles(tiles: Array<Array<S>>)
}