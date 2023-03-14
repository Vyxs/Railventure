package fr.manigames.railventure.test

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.common.map.BaseChunk
import fr.manigames.railventure.common.map.BaseMap

class TestMap : BaseMap() {

    fun load() {
        for (i in -1..1) {
            for (j in -1..1) {
                setChunk(i, j, makeChunk(i, j))
            }
        }
    }

    private fun makeChunk(x: Int, y: Int) : BaseChunk {
        val chunk = BaseChunk(x, y)

        for (i in 0..MAP_CHUNK_SIZE) {
            for (j in 0..MAP_CHUNK_SIZE) {
                chunk.setTile(i, j, 0, TileType.GRASS)
            }
        }
        return chunk
    }
}