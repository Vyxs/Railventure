package fr.manigames.railventure.api.map.generation

data class Biome(
    val id: Int,
    val name: String,
    val temperature: Int,
    val humidity: Int,
    val altitude: Int,
    val color: Int,
    val type: BiomeType
)