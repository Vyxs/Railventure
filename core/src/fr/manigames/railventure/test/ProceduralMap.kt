package fr.manigames.railventure.test

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.OpenSimplexNoise
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random

class ProceduralMap(
    chunkLoader: (RenderableChunk) -> Unit
) : RenderableMap(chunkLoader) {

    private val generationProgress = AtomicReference(0f)

    override fun getGenerationProgress(): Float = generationProgress.get()

    private val seed = 45354454L
    private val noise = OpenSimplexNoise(seed)

    override fun generate() {

        val mapSize = MAP_CHUNK_SIZE * 10
        val noiseGrid = mutableListOf(mutableListOf<Int>())
        var currentProgress = 0f

        for (y in 0 until mapSize) {
            noiseGrid.add(mutableListOf())
            for (x in 0 until mapSize) {
                val id = getIdUsingPerlinNoise(x, y)
                noiseGrid[y].add(id)
            }
        }

        // fill of water
        for (y in 0 until mapSize / MAP_CHUNK_SIZE)
            for (x in 0 until mapSize / MAP_CHUNK_SIZE)
                setChunk(x, y, RenderableChunk(x, y).apply {
                    for (i in 0 until MAP_CHUNK_SIZE)
                        for (j in 0 until MAP_CHUNK_SIZE)
                            setTile(i, j, 0, TileType.WATER)
                })

        for (y in 0 until mapSize) {
            for (x in 0 until mapSize) {
                setTile(x, y, 0, TileType.fromCode(noiseGrid[y][x]))
                currentProgress += 1f / (mapSize * mapSize)
                generationProgress.set(currentProgress)
            }
        }
        generationProgress.set(1f)
    }

    private fun getIdUsingPerlinNoise(x: Int, y: Int): Int {
        val nx = x.toDouble() / (10 * 16) - 0.5
        val ny = y.toDouble() / (10 * 16) - 0.5
        val noise = noise.random2D(nx, ny)
        Logger.info("Noise at $x, $y : $noise")
        return when {
            noise < 0 -> TileType.WATER.code
            noise < 0.1 -> TileType.SAND.code
            noise < 0.2 -> TileType.DIRT.code
            else -> TileType.GRASS.code
        }
    }
}