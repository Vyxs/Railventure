package fr.manigames.railventure.test

import com.github.quillraven.fleks.World
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.base.TileLayer
import fr.manigames.railventure.api.map.biome.Biome
import fr.manigames.railventure.api.map.biome.TileWithProbability
import fr.manigames.railventure.api.registry.BiomeRegistry
import fr.manigames.railventure.api.registry.TileRegistry
import fr.manigames.railventure.common.generation.ProceduralHandler
import kotlin.math.abs
import kotlin.random.Random

class ProceduralHandler(
    seed: Long,
    biomeRegistry: BiomeRegistry,
    tileRegistry: TileRegistry
) : ProceduralHandler {

    private var rng: Random = Random(seed)
    private val biomes = biomeRegistry.getAll().values.toList()
    private val tileMapper = tileRegistry.mapper

    //val ocean = BiomeInstance(BiomeData("ocean", "Ocean", 10, 100, 1000, emptyList(), 0x0000FF, BiomeType.AQUATIC))
    //val tropicalOcean = BiomeInstance(BiomeData("ocean_tropical", "Tropical Ocean", 30, 100, 1000, emptyList(), 0x2222FF, BiomeType.AQUATIC))
    //val beach = BiomeInstance(BiomeData("beach", "Beach", 25, 70, 1050, emptyList(), 0xFFD700, BiomeType.TERRESTRIAL))
   // val desert = BiomeInstance(BiomeData("desert", "Desert", 50, 20, 1250, emptyList(), 0xFFD700, BiomeType.TERRESTRIAL))

   // val plains = BiomeInstance(BiomeData("plains", "Plains", 20, 60, 1500, emptyList(), 0x00FF00, BiomeType.TERRESTRIAL))
    //val forest = BiomeInstance(BiomeData("forest", "Forest", 16, 80, 1750, emptyList(), 0x228B22, BiomeType.TERRESTRIAL))
   // val mountain = BiomeInstance(BiomeData("mountain", "Mountain", 12, 60, 2000, emptyList(), 0x808080, BiomeType.TERRESTRIAL))
   // val snowMountain = BiomeInstance(BiomeData("mountain_snowy", "Snowy Mountain", 0, 40, 2200, emptyList(), 0xFFFFFF, BiomeType.TERRESTRIAL))
    //val biomes = arrayOf(ocean, tropicalOcean, beach, desert, plains, forest, mountain, snowMountain)
    // biome -> array of tile types and their probability
   /* val biomeTiles = mapOf(
        ocean to arrayOf(Pair(TileType.DEEP_WATER, 1.0)),
        tropicalOcean to arrayOf(Pair(TileType.WATER, 1.0)),
        beach to arrayOf(Pair(TileType.CLEAR_SAND, 1.0)),
        desert to arrayOf(Pair(TileType.SAND, 0.99), Pair(TileType.DIRT, 0.01)),
        plains to arrayOf(Pair(TileType.GRASS, 0.96), Pair(TileType.FLOWER_GRASS, 0.03), Pair(TileType.TALL_GRASS, 0.01)),
        forest to arrayOf(Pair(TileType.GRASS, 0.9), Pair(TileType.TALL_GRASS, 0.08), Pair(TileType.DIRT, 0.01), Pair(
            TileType.MOSSY_STONE, 0.01)),
        mountain to arrayOf(Pair(TileType.STONE, 0.9), Pair(TileType.MOSSY_STONE, 0.1)),
        snowMountain to arrayOf(Pair(TileType.SNOW, 0.8), Pair(TileType.SNOWY_STONE, 0.19), Pair(TileType.STONE, 0.01))
    )*/
    val offset = 1.2
    val maxAltitude = biomes.maxOf { it.altitude } * offset
    val minAltitude = 0.0
    val maxHumidity = biomes.maxOf { it.humidity } * offset
    val minHumidity = biomes.minOf { it.humidity } * -offset
    val maxTemperature = biomes.maxOf { it.temperature } * offset
    val minTemperature = biomes.minOf { it.temperature } * -offset

    val grassGroup = arrayOf(
        TileType.FOLIAGE_SUMMER_BUSH_1,
        TileType.FOLIAGE_SUMMER_BUSH_2,
        TileType.FOLIAGE_SUMMER_BUSH_3,
        TileType.FOLIAGE_SUMMER_BUSH_4,
        TileType.FOLIAGE_SUMMER_BUSH_5,
        TileType.FOLIAGE_SUMMER_BUSH_6,
        TileType.FOLIAGE_SUMMER_BUSH_7,
        TileType.FOLIAGE_SUMMER_BUSH_8,
        TileType.FOLIAGE_SUMMER_TREE_1,
        TileType.FOLIAGE_SUMMER_TREE_2,
        TileType.FOLIAGE_SUMMER_TREE_3,
        TileType.FOLIAGE_SUMMER_TREE_4,
        TileType.FOLIAGE_SUMMER_TREE_5,
        TileType.FOLIAGE_SUMMER_TREE_6,
        TileType.FOLIAGE_SUMMER_FLOWER_1,
        TileType.FOLIAGE_SUMMER_FLOWER_2,
        TileType.FOLIAGE_SUMMER_FLOWER_3,
        TileType.FOLIAGE_SUMMER_FLOWER_4
    )

    val tallGrassGroup = arrayOf(
        TileType.FOLIAGE_SUMMER_TALL_BUSH_1,
        TileType.FOLIAGE_SUMMER_TALL_BUSH_2,
        TileType.FOLIAGE_SUMMER_TALL_BUSH_3,
        TileType.FOLIAGE_SUMMER_TALL_BUSH_4,
        TileType.FOLIAGE_SUMMER_TALL_BUSH_5,
        TileType.FOLIAGE_SUMMER_TALL_BUSH_6
    )

    val flowerGroup = arrayOf(
        TileType.FOLIAGE_SUMMER_FLOWER_1,
        TileType.FOLIAGE_SUMMER_FLOWER_2,
        TileType.FOLIAGE_SUMMER_FLOWER_3,
        TileType.FOLIAGE_SUMMER_FLOWER_4
    )

    val dirtGroup = arrayOf(
        TileType.FOLIAGE_AUTUMN_TREE_1,
        TileType.FOLIAGE_AUTUMN_TREE_2,
        TileType.FOLIAGE_AUTUMN_TREE_3,
        TileType.FOLIAGE_AUTUMN_TREE_4,
        TileType.FOLIAGE_AUTUMN_TREE_5,
        TileType.FOLIAGE_AUTUMN_TREE_6
    )

    val stoneGroup = arrayOf(
        TileType.FOLIAGE_SUMMER_CONIFER_1,
        TileType.FOLIAGE_SUMMER_CONIFER_2,
        TileType.FOLIAGE_SUMMER_CONIFER_3,
    )

    val snowGroup = arrayOf(
        TileType.FOLIAGE_WINTER_CONIFER_1,
        TileType.FOLIAGE_WINTER_CONIFER_2,
        TileType.FOLIAGE_WINTER_CONIFER_3,
        TileType.FOLIAGE_WINTER_ROCK_1,
        TileType.FOLIAGE_WINTER_ROCK_2,
        TileType.FOLIAGE_WINTER_ROCK_3,
        TileType.FOLIAGE_WINTER_TRUNK_1,
        TileType.FOLIAGE_WINTER_TRUNK_2,
        TileType.FOLIAGE_WINTER_TRUNK_3,
        TileType.FOLIAGE_WINTER_TRUNK_4,
    )

    override fun determineGameObjects(
        seed: Long,
        world: World,
        altitude: Double,
        humidity: Double,
        temperature: Double,
        tileX: Int,
        tileY: Int
    ): TileLayer {

        val altitudeValue = (altitude + 1) / 2 * (maxAltitude - minAltitude) + minAltitude
        val humidityValue = (humidity + 1) / 2 * (maxHumidity - minHumidity) + minHumidity
        val temperatureValue = (temperature + 1) / 2 * (maxTemperature - minTemperature) + minTemperature

        val biome = biomes.minBy { abs(it.altitude - altitudeValue) + abs(it.humidity - humidityValue) + abs(it.temperature - temperatureValue) }

        val tileLayer = TileLayer()

        var type = determineTileType(biome)//determineTileTypeUsingProbability(biomeTiles[biome])

        if (tileX == 10 && tileY == 10) {
            type = tileMapper["clear_sand"]
            tileLayer[Metric.MAP_OBJECT_LAYER] = TileType.FOLIAGE_SPRING_CONIFER_1.code
        } else if (tileX == 5 && tileY == 10) {
            type = tileMapper["clear_sand"]
            tileLayer[Metric.MAP_OBJECT_LAYER] = TileType.FOLIAGE_SUMMER_FLOWER_4.code
        } else if (tileX == 15 && tileY == 10) {
            type = tileMapper["clear_sand"]
            tileLayer[Metric.MAP_OBJECT_LAYER] = TileType.FOLIAGE_SPRING_ROCK_3.code
        }

        tileLayer[Metric.MAP_GROUND_LAYER] = type

        val objectType = when (type) {
            tileMapper["grass"] -> grassGroup.random(rng, TileType.FOLIAGE_SUMMER_BUSH_1)
            tileMapper["tall_grass"] -> tallGrassGroup.random(rng, TileType.FOLIAGE_SUMMER_TALL_BUSH_1)
            tileMapper["flower_grass"] -> flowerGroup.random(rng, TileType.FOLIAGE_SUMMER_FLOWER_1)
            tileMapper["dirt"] -> dirtGroup.random(rng, TileType.FOLIAGE_AUTUMN_TREE_1)
            tileMapper["stone"] -> stoneGroup.random(rng, TileType.FOLIAGE_SUMMER_CONIFER_1)
            tileMapper["snow"] -> snowGroup.random(rng, TileType.FOLIAGE_WINTER_CONIFER_1)
            else -> null
        }

        if (objectType != null) {
            rng.let { random ->
                if (random.nextFloat() > 0.95f) {
                    tileLayer[Metric.MAP_OBJECT_LAYER] = objectType.code
                }
            }
        }
        tileLayer[Metric.MAP_OBJECT_LAYER] = when {
            (tileY == 1 || tileY == 5) && tileX in -17..0 -> TileType.RAIL_H.code
            (tileX == 1 || tileX == -18) && tileY in 2..4 -> TileType.RAIL_V.code
            tileX == 1 && tileY == 1 -> TileType.RAIL_TOP_LEFT.code
            tileX == 1 && tileY == 5 -> TileType.RAIL_BOT_LEFT.code
            tileX == -18 && tileY == 1 -> TileType.RAIL_TOP_RIGHT.code
            tileX == -18 && tileY == 5 -> TileType.RAIL_BOT_RIGHT.code
            else -> tileLayer[Metric.MAP_OBJECT_LAYER]
        }

        // y ; x
        tileLayer[Metric.MAP_OBJECT_LAYER] = when {
            tileY == 0 && tileX == 0 -> TileType.DEBUG_TILE_0_0.code
            tileY == 0 && tileX == 15 -> TileType.DEBUG_TILE_0_15.code
            tileY == 15 && tileX == 0 -> TileType.DEBUG_TILE_15_0.code
            tileY == 15 && tileX == 15 -> TileType.DEBUG_TILE_15_15.code
            tileY == 16 && tileX == 16 -> TileType.DEBUG_TILE_0_0.code
            tileY == 16 && tileX == 31 -> TileType.DEBUG_TILE_0_15.code
            tileY == 31 && tileX == 16 -> TileType.DEBUG_TILE_15_0.code
            tileY == 31 && tileX == 31 -> TileType.DEBUG_TILE_15_15.code
            else -> tileLayer[Metric.MAP_OBJECT_LAYER]
        }

        return tileLayer
    }

    private fun determineTileType(biome: Biome): Int {
        if (biome.tiles.isEmpty())
            return 0
        return tileMapper[biome.tiles.random(rng, "air")]
    }

    private fun List<TileWithProbability>.random(rng: Random, default: String): String {
        if (isEmpty()) {
            return default
        }
        val randomValue = rng.nextFloat()
        var cumulativeProbability = 0f

        for (tile in this) {
            cumulativeProbability += tile.probability
            if (randomValue <= cumulativeProbability) {
                return tile.key
            }
        }
        return default
    }

    private fun <T> Array<T>.random(rng: Random, default: T): T {
        return if (isEmpty()) default else get(rng.nextInt(size))
    }
}