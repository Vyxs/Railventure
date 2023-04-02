package fr.manigames.railventure.common.map

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.MapChunk
import fr.manigames.railventure.api.map.TileLayer

open class BaseChunk(val x: Int, val y: Int) : MapChunk<TileType> {

    /**
     * The tiles of the chunk
     * The first dimension is the y position
     * The second dimension is the x position
     **/
    private val tiles = Array(MAP_CHUNK_SIZE) { Array(MAP_CHUNK_SIZE) { TileLayer() } }

    /**
     * Get the tile at the given position
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position depth
     * @return the tile at the given position
     */
    override fun getTile(x: Int, y: Int, z: Int): TileType {
        return TileType.values().first { it.code == tiles[y][x][z] }
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
    override fun setTile(x: Int, y: Int, z: Int, tileType: TileType) {
        tiles[y][x][z] = tileType.code
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

    override fun getTileWorldPosition(x: Int, y: Int): Pair<Int, Int> {
        return Pair(this.y * MAP_CHUNK_SIZE + y, this.x * MAP_CHUNK_SIZE + x)
    }
}
