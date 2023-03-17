package fr.manigames.railventure.api.map

/**
 * A chunk of the map. It contains a 2D array of tiles
 */
interface MapChunk<V> {

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

}