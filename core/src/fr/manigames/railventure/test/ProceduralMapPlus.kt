package fr.manigames.railventure.test

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.OpenSimplexNoise
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import java.util.concurrent.atomic.AtomicReference

class ProceduralMapPlus(
    chunkLoader: (RenderableChunk) -> Unit
) : RenderableMap(chunkLoader) {

    private val generationProgress = AtomicReference(0f)
    private val seed = 4455L
    private val altitude = OpenSimplexNoise(seed)
    private val humidity = OpenSimplexNoise(seed + 1)
    private val temperature = OpenSimplexNoise(seed + 2)
    private val scale = 10.0

    override fun getGenerationProgress(): Float = generationProgress.get()

    override fun generate() {
        generateChunk(0, 1)
        generateChunk(0, 0)
        generateChunk(0, -1)
        generateChunk(1, 1)
        generateChunk(1, 0)
        generateChunk(1, -1)
        generateChunk(2, 1)
        generateChunk(2, 0)
        generateChunk(2, -1)
        generationProgress.set(1f)
    }

    override fun generateChunk(x: Int, y: Int) {
        val chunk = RenderableChunk(x, y)
        val size = Pair((MAP_CHUNK_SIZE * x).toDouble(), (MAP_CHUNK_SIZE * y).toDouble())
        for (tileX in 0 until MAP_CHUNK_SIZE)
            for (tileY in 0 until MAP_CHUNK_SIZE) {
                val nx = (size.first + tileX) / scale
                val ny = size.second + tileY / scale
                val alt = altitude.random2D(nx, ny)
                val hum = humidity.random2D(nx, ny)
                val temp = temperature.random2D(nx, ny)
                chunk.setTile(tileX, tileY, 0, getTileType(alt, hum, temp))
            }
        setChunk(chunk)
    }

    private fun getTileType(alt: Double, hum: Double, temp: Double): TileType {
        return when {
            alt < 0.2 -> TileType.WATER
            alt < 0.3 -> TileType.SAND
            alt < 0.4 -> TileType.DIRT
            alt < 0.5 -> TileType.GRASS
            alt < 0.6 -> TileType.MOSSY_STONE
            else -> TileType.STONE
        }
    }
}