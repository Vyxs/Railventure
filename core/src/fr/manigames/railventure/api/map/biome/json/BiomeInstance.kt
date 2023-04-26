package fr.manigames.railventure.api.map.biome.json

import fr.manigames.railventure.api.map.biome.Biome
import fr.manigames.railventure.api.map.biome.BiomeType
import fr.manigames.railventure.api.serialize.json.Json

class InvalidBiomeModelException(message: String) : Exception(message)

class BiomeInstance(biomeData: BiomeData) : Biome() {

    companion object {

        fun fromJsonModel(json: String?): Biome {
            return try {
                val biomeData = Json().fromJson(json, BiomeData::class.java)
                BiomeInstance(biomeData)
            } catch (e: Exception) {
                throw InvalidBiomeModelException("Invalid biome model: ${e.message}")
            }
        }
    }

    override val key: String = biomeData.key
    override val name: String = biomeData.name
    override val temperature: Int = biomeData.temperature
    override val humidity: Int = biomeData.humidity
    override val altitude: Int = biomeData.altitude
    override val color: Int = biomeData.color
    override val type: BiomeType = biomeData.type
}