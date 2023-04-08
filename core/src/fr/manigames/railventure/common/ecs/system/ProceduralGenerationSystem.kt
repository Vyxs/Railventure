package fr.manigames.railventure.common.ecs.system

import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.ecs.component.ComponentType
import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.*
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.map.ChunkLoader
import fr.manigames.railventure.common.ecs.component.WorldPositionComponent
import kotlin.math.abs

class ProceduralGenerationSystem(
    world: World,
    private val map: ProceduralMap
) : System(world), ProceduralTileHandler {

    private val chunkLoader = ChunkLoader(Assets.instance)
    private val viewDistance = 4
    private val mapConfig = ProceduralMapConfig(
        seed = 4455,
        defaultGenerationSize = 20,
        regenerateOnConfigChange = false
    )
    override fun init() {
        map.setChunkLoader(chunkLoader::loadChunk)
        map.setProceduralMapConfig(mapConfig)
        map.setTileHandler(this)
        kotlin.run { map.generate() }
    }

    override fun update(delta: Float) {
        val chunkToGenerate = mutableListOf<Pair<Int, Int>>()

        world.getEntitiesWithComponents(ComponentType.PLAYER).forEach { entry ->
            val pos = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent
            val chunkPosition = PosUtil.getChunkPosition(pos.world_x, pos.world_y)
            val area = ChunkArea.fromCenter(chunkPosition.first, chunkPosition.second, viewDistance)

            area.toChunkPositions().forEach { chunkPos ->
                if (!map.hasChunk(chunkPos.first, chunkPos.second)) {
                    chunkToGenerate.add(chunkPos)
                }
            }
        }

        chunkToGenerate.forEach { chunkPos ->
            map.generateChunk(chunkPos.first, chunkPos.second)
        }
    }

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
        forest to arrayOf(Pair(TileType.GRASS, 0.9), Pair(TileType.TALL_GRASS, 0.08), Pair(TileType.DIRT, 0.01), Pair(
            TileType.MOSSY_STONE, 0.01)),
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

        if (altitude < -0.2) {
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