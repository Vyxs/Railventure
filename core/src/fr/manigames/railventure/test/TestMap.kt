package fr.manigames.railventure.test

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.common.map.BaseChunk
import fr.manigames.railventure.common.map.BaseMap
import java.time.Instant

class TestMap : BaseMap() {

    fun load() {
        val instantA = System.currentTimeMillis()
        val size = Pair(20, 20)
        for (i in -size.first..size.first) {
            for (j in -size.second..size.second) {
                setChunk(i, j, makeChunk(i, j))
            }
        }
        val instantB = System.currentTimeMillis()
        Logger.info("Map loaded in ${instantB - instantA}ms")
    }

    private fun makeChunk(x: Int, y: Int) : BaseChunk {
        val chunk = BaseChunk(x, y)

        for (i in 0 until MAP_CHUNK_SIZE) {
            for (j in 0 until MAP_CHUNK_SIZE) {
                chunk.setTile(i, j, 0, TileType.GRASS)
            }
        }
        return chunk
    }
}