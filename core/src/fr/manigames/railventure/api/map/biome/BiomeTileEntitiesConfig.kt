package fr.manigames.railventure.api.map.biome

data class BiomeTileEntitiesConfig(
    val spawnOn: List<String>,
    val keys: List<String>,
    val odds: Float
)