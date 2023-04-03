package fr.manigames.railventure.test

import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.procedural.ProceduralMap
import fr.manigames.railventure.api.map.procedural.ProceduralMapConfig
import fr.manigames.railventure.api.map.procedural.ProceduralTileHandler
import fr.manigames.railventure.client.map.RenderableChunk

class TestProceduralMap(
    chunkLoader: (RenderableChunk) -> Unit
) : ProceduralMap(mapConfig, this, chunkLoader) {

    companion object : ProceduralTileHandler {

        val mapConfig = ProceduralMapConfig(
            seed = 4455,
            defaultGenerationSize = 200,
            regenerateOnConfigChange = false
        )

        override fun determineTileType(
            altitude: Double,
            humidity: Double,
            temperature: Double,
            tileX: Int,
            tileY: Int
        ): TileType {
            return when {
                altitude < 0 -> TileType.WATER
                altitude < 0.1 -> TileType.SAND
                altitude < 0.12 -> TileType.DIRT
                altitude < 0.5 -> TileType.GRASS
                altitude < 0.6 -> TileType.MOSSY_STONE
                else -> TileType.STONE
            }
        }
    }
}