package fr.manigames.railventure.test

import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.ProceduralMap
import fr.manigames.railventure.api.map.generation.ProceduralMapConfig
import fr.manigames.railventure.api.map.generation.ProceduralTileHandler
import fr.manigames.railventure.client.map.RenderableChunk
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore

class ParallelProceduralMap(
    chunkLoader: (RenderableChunk) -> Unit
) : ProceduralMap(mapConfig, this, chunkLoader) {

    companion object : ProceduralTileHandler {

        val mapConfig = ProceduralMapConfig(
            seed = 4455,
            defaultGenerationSize = 100,
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

    override fun generate() {
        runBlocking {
            generateMapInParallel()
        }
    }

    private suspend fun generateMapInParallel() = coroutineScope {
        val size = proceduralMapConfig.defaultGenerationSize
        val maxConcurrentChunks = getMaxConcurrentChunks()
        val semaphore = Semaphore(maxConcurrentChunks)
        val jobs = mutableListOf<Job>()

        for (x in -(size / 2) until (size / 2)) {
            for (y in -(size / 2) until (size / 2)) {
                val chunkX = proceduralMapConfig.defaultGenerationPosition.first + x
                val chunkY = proceduralMapConfig.defaultGenerationPosition.second + y
                semaphore.acquire()
                val job = launch(Dispatchers.Default) {
                    generateChunk(chunkX, chunkY)
                    generationProgress.set((x + (size / 2)) / size.toFloat())
                    semaphore.release()
                }
                jobs.add(job)
            }
        }
        jobs.forEach { it.join() }
        generationProgress.set(1f)
    }

    private fun getMaxConcurrentChunks(): Int {
        val availableProcessors = Runtime.getRuntime().availableProcessors()
        return when {
            availableProcessors <= 2 -> 1 // Single or dual-core, run tasks sequentially
            availableProcessors <= 4 -> 2 // Quad-core, run up to 2 tasks concurrently
            else -> (availableProcessors * 0.75).toInt() // For more cores, use 75% of available cores
        }
    }
}