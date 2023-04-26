package fr.manigames.railventure.api.map.generation

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore

class ParallelProceduralMap : ProceduralMap() {

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