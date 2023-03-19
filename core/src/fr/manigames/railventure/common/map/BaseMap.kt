package fr.manigames.railventure.common.map

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.Map
import fr.manigames.railventure.api.map.MapChunk

open class BaseMap : Map<TileType> {

    companion object {
        fun toChunkId(x: Int, y: Int): Long {
            return x.toLong() shl 32 or y.toLong()
        }

        fun fromChunkId(id: Long): Pair<Int, Int> {
            return Pair((id shr 32).toInt(), id.toInt())
        }
    }

    protected val chunks: HashMap<Long, MapChunk<TileType>> = HashMap(9)

    override fun getChunk(x: Int, y: Int): MapChunk<TileType> {
        return chunks[toChunkId(x, y)] ?: BaseChunk(x, y)
    }

    override fun isChunkLoaded(x: Int, y: Int): Boolean {
        return chunks.containsKey(toChunkId(x, y))
    }

    override fun getTile(tileX: Int, tileY: Int, tileZ: Int): TileType {
        return getChunk(tileX / MAP_CHUNK_SIZE, tileY / MAP_CHUNK_SIZE).getTile(tileX % MAP_CHUNK_SIZE, tileY % MAP_CHUNK_SIZE, tileZ)
    }

    override fun setTile(tileX: Int, tileY: Int, tileZ: Int, tileType: TileType) {
        getChunk(tileX / MAP_CHUNK_SIZE, tileY / MAP_CHUNK_SIZE).setTile(tileX % MAP_CHUNK_SIZE, tileY % MAP_CHUNK_SIZE, tileZ, tileType)
    }

    override fun setChunk(x: Int, y: Int, chunk: MapChunk<TileType>) {
        chunks[toChunkId(x, y)] = chunk
    }
}