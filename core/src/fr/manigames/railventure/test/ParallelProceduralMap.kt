package fr.manigames.railventure.test

import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.*
import fr.manigames.railventure.client.map.RenderableChunk
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlin.math.abs

class ParallelProceduralMap(
    chunkLoader: (RenderableChunk) -> Unit
) : ProceduralMap(mapConfig, this, chunkLoader) {

    companion object : ProceduralTileHandler {

        val mapConfig = ProceduralMapConfig(
            seed = 4455,
            defaultGenerationSize = 100,
            regenerateOnConfigChange = false
        )

        val ocean = Biome(0, "Ocean", 10, 100, 0, 0x0000FF, BiomeType.AQUATIC)
        val tropicalOcean = Biome(1, "Tropical Ocean", 30, 100, 0, 0x0000FF, BiomeType.AQUATIC)
        val beach = Biome(2, "Beach", 25, 70, 10, 0xFFD700, BiomeType.TERRESTRIAL)
        val desert = Biome(3, "Desert", 50, 20, 50, 0xFFD700, BiomeType.TERRESTRIAL)
        val plains = Biome(4, "Plains", 20, 60, 100, 0x00FF00, BiomeType.TERRESTRIAL)
        val forest = Biome(5, "Forest", 16, 80, 150, 0x228B22, BiomeType.TERRESTRIAL)
        val mountain = Biome(6, "Mountain", 12, 60, 200, 0x808080, BiomeType.TERRESTRIAL)
        val snowMountain = Biome(7, "Snowy Mountain", 0, 40, 220, 0xFFFFFF, BiomeType.TERRESTRIAL)

        val biomes = arrayOf(ocean, tropicalOcean, beach, desert, plains, forest, mountain, snowMountain)

        // biome -> array of tile types and their probability
        val biomeTiles = mapOf(
            ocean to arrayOf(Pair(TileType.DEEP_WATER, 1.0)),
            tropicalOcean to arrayOf(Pair(TileType.WATER, 1.0)),
            beach to arrayOf(Pair(TileType.CLEAR_SAND, 1.0)),
            desert to arrayOf(Pair(TileType.SAND, 0.99), Pair(TileType.DIRT, 0.01)),
            plains to arrayOf(Pair(TileType.GRASS, 0.96), Pair(TileType.FLOWER_GRASS, 0.03), Pair(TileType.TALL_GRASS, 0.01)),
            forest to arrayOf(Pair(TileType.GRASS, 0.9), Pair(TileType.TALL_GRASS, 0.08), Pair(TileType.DIRT, 0.01), Pair(TileType.MOSSY_STONE, 0.01)),
            mountain to arrayOf(Pair(TileType.STONE, 0.9), Pair(TileType.MOSSY_STONE, 0.1)),
            snowMountain to arrayOf(Pair(TileType.SNOW, 0.8), Pair(TileType.SNOWY_STONE, 0.19), Pair(TileType.STONE, 0.01))
        )

        val offset = 1.2

        val maxAltitude = biomes.maxOf { it.altitude } * offset
        val minAltitude = biomes.minOf { it.altitude } * -offset
        val maxHumidity = biomes.maxOf { it.humidity } * offset
        val minHumidity = biomes.minOf { it.humidity } * -offset
        val maxTemperature = biomes.maxOf { it.temperature } * offset
        val minTemperature = biomes.minOf { it.temperature } * -offset

        override fun determineTileType(
            altitude: Double, // -1.0 to 1.0
            humidity: Double, // -1.0 to 1.0
            temperature: Double, // -1.0 to 1.0
            tileX: Int,
            tileY: Int
        ): TileType {
            val altitudeValue = (altitude + 1) / 2 * (maxAltitude - minAltitude) + minAltitude
            val humidityValue = (humidity + 1) / 2 * (maxHumidity - minHumidity) + minHumidity
            val temperatureValue = (temperature + 1) / 2 * (maxTemperature - minTemperature) + minTemperature

            var biome = biomes.minByOrNull {
                abs(it.altitude - altitudeValue) +
                        abs(it.humidity - humidityValue) +
                        abs(it.temperature - temperatureValue)
            } ?: ocean

            if (altitude < -0.1) {
                biome = ocean
            } else if (altitude < 0 && temperature > 0) {
                biome = tropicalOcean
            }

            return determineTileTypeUsingProbability(biomeTiles[biome])
        }

        private fun determineTileTypeUsingProbability(tileTypes: Array<Pair<TileType, Double>>?): TileType {
            val random = Math.random()
            var sum = 0.0
            for (tileType in tileTypes!!) {
                sum += tileType.second
                if (random < sum) {
                    return tileType.first
                }
            }
            return tileTypes[0].first
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