package fr.manigames.railventure.common.map

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.map.base.MapChunk
import fr.manigames.railventure.api.map.base.TileLayer

open class BaseChunk(val x: Int, val y: Int) : MapChunk<Int, TileLayer> {

    /**
     * The tiles of the chunk
     * The first dimension is the y position
     * The second dimension is the x position
     **/
    private val tiles = Array(MAP_CHUNK_SIZE) { Array(MAP_CHUNK_SIZE) { TileLayer() } }

    /**
     * Get the x position of the chunk
     *
     * @return the x position of the chunk
     */
    override fun getChunkX(): Int = x

    /**
     * Get the y position of the chunk
     *
     * @return the y position of the chunk
     */
    override fun getChunkY(): Int = y

    /**
     * Get the tile at the given position
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position depth
     * @return the tile at the given position
     */
    override fun getTile(x: Int, y: Int, z: Int): Int {
        return tiles[y][x][z]
    }

    /**
     * Get the tile stack at the given position
     *
     * @param x the x position
     * @param y the y position
     * @return the stack of tiles
     **/
    override fun getTileStack(x: Int, y: Int): TileLayer {
        return tiles[y][x]
    }

    /**
     * Get the tiles of the chunk.
     * The first dimension is the y position
     * The second dimension is the x position
     *
     * @return the tiles of the chunk
     */
    override fun getTiles(): Array<Array<TileLayer>> {
        return tiles
    }

    /**
     * Set the tile at the given position
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position depth
     * @param tileType the tile type
     */
    override fun setTile(x: Int, y: Int, z: Int, tileType: Int) {
        tiles[y][x][z] = tileType
    }

    /**
     * Set the tile stack at the given position
     *
     * @param x the x position
     * @param y the y position
     * @param stack the stack of tiles
     **/
    override fun setTileStack(x: Int, y: Int, stack: TileLayer) {
        tiles[y][x] = stack
    }

    /**
     * Set the tiles of the chunk
     *
     * @param tiles the tiles of the chunk
     */
    override fun setTiles(tiles: Array<Array<TileLayer>>) {
        this.tiles.forEachIndexed { x, column ->
            column.forEachIndexed { y, _ ->
                this.tiles[y][x] = tiles[y][x]
            }
        }
    }
}
