package fr.manigames.railventure.test

import com.github.quillraven.fleks.World
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.base.TileLayer
import fr.manigames.railventure.api.map.generation.Biome
import fr.manigames.railventure.api.map.generation.BiomeType
import fr.manigames.railventure.common.generation.ProceduralHandler
import kotlin.math.abs
import kotlin.random.Random

class ProceduralHandler : ProceduralHandler {

    private var rng: Random? = null

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
        if (rng == null) {
            rng = Random(seed)
        }
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

        val tileLayer = TileLayer()

        var type = determineTileTypeUsingProbability(biomeTiles[biome])

        if (tileX == 10 && tileY == 10) {
            type = TileType.CLEAR_SAND
            tileLayer[Metric.MAP_OBJECT_LAYER] = TileType.FOLIAGE_SPRING_CONIFER_1.code
        } else if (tileX == 5 && tileY == 10) {
            type = TileType.CLEAR_SAND
            tileLayer[Metric.MAP_OBJECT_LAYER] = TileType.FOLIAGE_SUMMER_FLOWER_4.code
        } else if (tileX == 15 && tileY == 10) {
            type = TileType.CLEAR_SAND
            tileLayer[Metric.MAP_OBJECT_LAYER] = TileType.FOLIAGE_SPRING_ROCK_3.code
        }


        tileLayer[Metric.MAP_GROUND_LAYER] = type.code

        val objectType = when (type) {
            TileType.GRASS -> grassGroup.random(rng!!, TileType.FOLIAGE_SUMMER_BUSH_1)
            TileType.TALL_GRASS -> tallGrassGroup.random(rng!!, TileType.FOLIAGE_SUMMER_TALL_BUSH_1)
            TileType.FLOWER_GRASS -> flowerGroup.random(rng!!, TileType.FOLIAGE_SUMMER_FLOWER_1)
            TileType.DIRT -> dirtGroup.random(rng!!, TileType.FOLIAGE_AUTUMN_TREE_1)
            TileType.STONE -> stoneGroup.random(rng!!, TileType.FOLIAGE_SUMMER_CONIFER_1)
            TileType.SNOW -> snowGroup.random(rng!!, TileType.FOLIAGE_WINTER_CONIFER_1)
            else -> null
        }

        if (objectType != null) {
            rng?.let { random ->
                if (random.nextFloat() > 0.95f) {
                    tileLayer[Metric.MAP_OBJECT_LAYER] = objectType.code
                }
            }
        }
        return tileLayer
    }

    private fun <T> Array<T>.random(rng: Random, default: T): T {
        return if (isEmpty()) default else get(rng.nextInt(size))
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