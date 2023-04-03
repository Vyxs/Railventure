package fr.manigames.railventure.common.map

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.Map
import fr.manigames.railventure.api.map.MapChunk

open class BaseMap : Map<TileType> {

    companion object {

        /**
         * Convert a pair of chunk coordinates to a unique id. This id is used to store the chunk position in a HashMap.
         * This handle negative coordinates.
         */
        fun toChunkId(x: Int, y: Int): Long {
            return (x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFFL)
        }

        /**
         * Convert a chunk id to a pair of chunk coordinates.
         */
        fun fromChunkId(id: Long): Pair<Int, Int> {
            return Pair((id shr 32).toInt(), (id and 0xFFFFFFFFL).toInt())
        }
    }

    protected val chunks: HashMap<Long, MapChunk<TileType>> = HashMap(9)

    override fun getChunk(x: Int, y: Int): MapChunk<TileType>? {
        return chunks[toChunkId(x, y)]
    }

    override fun hasChunk(x: Int, y: Int): Boolean {
        return chunks.containsKey(toChunkId(x, y))
    }

    override fun getTile(tileX: Int, tileY: Int, tileZ: Int): TileType? {
        return getChunk(tileX / MAP_CHUNK_SIZE, tileY / MAP_CHUNK_SIZE)?.getTile(tileX % MAP_CHUNK_SIZE, tileY % MAP_CHUNK_SIZE, tileZ)
    }

    /**
     * Set the tile at the given position. If the chunk is not loaded, it will not be set.
     *
     * @param tileX The x position of the tile
     * @param tileY The y position of the tile
     * @param tileZ The z position of the tile
     * @param tileType The tile type to set
     *
     * @return True if the tile has been set, false otherwise
     **/
    override fun setTile(tileX: Int, tileY: Int, tileZ: Int, tileType: TileType) : Boolean {
        val chunk = getChunk(tileX / MAP_CHUNK_SIZE, tileY / MAP_CHUNK_SIZE)
        return if (chunk != null) {
            chunk.setTile(tileX % MAP_CHUNK_SIZE, tileY % MAP_CHUNK_SIZE, tileZ, tileType)
            true
        } else false
    }

    /**
     * Set the chunk at the given position. You must ensure that the chunk coordinates are the same as the chunk position parameter.
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @param chunk The chunk to set
     **/
    override fun setChunk(chunk: MapChunk<TileType>) {
        chunks[toChunkId(chunk.getChunkX(), chunk.getChunkY())] = chunk
    }
}