package fr.manigames.railventure.common.map

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.base.Map
import fr.manigames.railventure.api.map.base.MapChunk
import fr.manigames.railventure.api.map.base.TileLayer
import fr.manigames.railventure.api.util.PosUtil

open class BaseMap : Map<TileType, TileLayer> {

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

    protected val chunks: HashMap<Long, MapChunk<TileType, TileLayer>> = HashMap(9)

    override fun getChunk(x: Int, y: Int): MapChunk<TileType, TileLayer>? {
        return chunks[toChunkId(x, y)]
    }

    override fun hasChunk(x: Int, y: Int): Boolean {
        return chunks.containsKey(toChunkId(x, y))
    }

    override fun getTile(tileX: Int, tileY: Int, tileZ: Int): TileType? {
        return getChunk(tileX / MAP_CHUNK_SIZE, tileY / MAP_CHUNK_SIZE)?.getTile(tileX % MAP_CHUNK_SIZE, tileY % MAP_CHUNK_SIZE, tileZ)
    }

    /**
     * Get the first non-air tile at the given position. If the chunk is not loaded, it will return null.
     *
     * @param tileX The x position of the tile
     * @param tileY The y position of the tile
     * @return The first non-air tile at the given position, or null if the chunk is not loaded
     **/
    fun getFirstNonAirTile(tileX: Float, tileY: Float): TileType? {
        val (chunkX, chunkY) = PosUtil.getChunkPosition(tileX, tileY)
        val chunk = getChunk(chunkX, chunkY) ?: return null

        val (posX, posY) = PosUtil.getPosInChunk(tileX, tileY)
        val tileStack = chunk.getTileStack(posX, posY)

        for (tileCode in tileStack.reversed()) {
            if (tileCode != TileType.AIR.code) {
                return TileType.fromCode(tileCode)
            }
        }
        return null
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
    override fun setChunk(chunk: MapChunk<TileType, TileLayer>) {
        chunks[toChunkId(chunk.getChunkX(), chunk.getChunkY())] = chunk
    }
}