package fr.manigames.railventure.api.map.biome.json

import fr.manigames.railventure.api.map.biome.Biome
import fr.manigames.railventure.api.map.biome.BiomeType

data class BiomeData(
    val key: String,
    val name: String,
    val temperature: Int,
    val humidity: Int,
    val altitude: Int,
    val color: Int = Biome.BIOME_DEFAULT_COLOR,
    val type: BiomeType = BiomeType.TERRESTRIAL
)