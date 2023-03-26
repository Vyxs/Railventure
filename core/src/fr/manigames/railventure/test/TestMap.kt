package fr.manigames.railventure.test

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.common.map.BaseChunk

class TestMap(
    chunkLoader: (RenderableChunk) -> Unit
) : RenderableMap(chunkLoader) {

    private val rand = java.security.SecureRandom()
    private val stressTest = true

    fun generate() {
        val instantA = System.currentTimeMillis()
        val size = Pair(40, 40)
        for (i in -size.first..size.first) {
            for (j in -size.second..size.second) {
                setChunk(i, j, makeChunk(i, j))
            }
        }
        val instantB = System.currentTimeMillis()
        Logger.info("Map generated in ${instantB - instantA}ms")
    }

    private fun makeChunk(x: Int, y: Int) : BaseChunk {
        val chunk = RenderableChunk(x, y)
        val tiles0 = setOf(TileType.GRASS, TileType.DIRT, TileType.SAND, TileType.WATER)
        val tiles1 = setOf(TileType.RAIL_V, TileType.RAIL_H, TileType.RAIL_X, TileType.RAIL_BOT_RIGHT,
            TileType.RAIL_BOT_LEFT, TileType.RAIL_TOP_RIGHT, TileType.RAIL_TOP_LEFT, TileType.AIR,
            TileType.AIR, TileType.AIR, TileType.AIR, TileType.AIR, TileType.AIR, TileType.AIR)

        for (i in 0 until MAP_CHUNK_SIZE) {
            for (j in 0 until MAP_CHUNK_SIZE) {
                if (stressTest) {
                    chunk.setTile(i, j, 0, tiles0.elementAt(rand.nextInt(tiles0.size)))
                    chunk.setTile(i, j, 1, tiles1.elementAt(rand.nextInt(tiles1.size)))
                } else {
                    chunk.setTile(i, j, 0, TileType.GRASS)
                }
            }
        }
        return chunk
    }
}