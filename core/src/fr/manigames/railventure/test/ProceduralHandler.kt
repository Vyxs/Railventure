package fr.manigames.railventure.test

import com.github.quillraven.fleks.World
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.map.base.TileLayer
import fr.manigames.railventure.api.map.biome.Biome
import fr.manigames.railventure.api.map.biome.BiomeTileEntitiesConfig
import fr.manigames.railventure.api.registry.BiomeRegistry
import fr.manigames.railventure.api.registry.TileEntityRegistry
import fr.manigames.railventure.api.registry.TileRegistry
import fr.manigames.railventure.common.generation.ProceduralHandler
import kotlin.math.abs
import kotlin.random.Random

class ProceduralHandler(
    seed: Long,
    biomeRegistry: BiomeRegistry,
    tileRegistry: TileRegistry,
    tileEntityRegistry: TileEntityRegistry
) : ProceduralHandler {

    private var rng: Random = Random(seed)
    private val biomes = biomeRegistry.getAll().values.toList()
    private val tileMapper = tileRegistry.mapper
    private val tileEntityMapper = tileEntityRegistry.mapper

    private val offset = 1.2
    private val maxAltitude = biomes.maxOf { it.altitude } * offset
    private val minAltitude = 0.0
    private val maxHumidity = biomes.maxOf { it.humidity } * offset
    private val minHumidity = biomes.minOf { it.humidity } * -offset
    private val maxTemperature = biomes.maxOf { it.temperature } * offset
    private val minTemperature = biomes.minOf { it.temperature } * -offset

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
        val tileKey = determineTileType(biome)
        val tileCode = tileMapper[tileKey]

        tileLayer[Metric.MAP_GROUND_LAYER] = tileCode

        determineTileEntity(tileKey, biome.tileEntities)?.let { tileEntityKey ->
            tileLayer[Metric.MAP_OBJECT_LAYER] = tileEntityMapper[tileEntityKey]
        }
        return tileLayer
    }

    private fun determineTileEntity(tile: String, configList: List<BiomeTileEntitiesConfig>): String? {
        if (configList.isEmpty()) {
            return null
        }
        val validConfigs = configList.filter { it.spawnOn.contains(tile) }

        if (validConfigs.isEmpty()) {
            return null
        }
        val sortedConfigs = validConfigs.sortedByDescending { it.odds }

        for (config in sortedConfigs) {
            val randomValue = rng.nextDouble()

            if (randomValue <= config.odds) {
                return config.keys[rng.nextInt(config.keys.size)]
            }
        }
        return null
    }

    private fun determineTileType(biome: Biome): String {
        if (biome.tiles.isEmpty())
            return "air"
        val randomValue = rng.nextFloat()
        var cumulativeProbability = 0f

        for (tile in biome.tiles) {
            cumulativeProbability += tile.probability
            if (randomValue <= cumulativeProbability) {
                return tile.key
            }
        }
        return "air"
    }
}