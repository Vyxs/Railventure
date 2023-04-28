package fr.manigames.railventure.api.map.biome

import fr.manigames.railventure.api.registry.RegistryObject

abstract class Biome : RegistryObject {
    companion object {
        const val BIOME_DEFAULT_COLOR = 0xA020F0 // purple
        val BIOME_DEFAULT_TYPE = BiomeType.TERRESTRIAL
        val BIOME_DEFAULT_GRADIENT = BiomeGradient.UNSET
    }

    abstract override val key: String
    abstract val name: String
    abstract val temperature: Int
    abstract val humidity: Int
    abstract val altitude: Int
    abstract val tiles: List<TileWithProbability>
    @Transient open val color: Int = 0xA020F0
    @Transient open val type: BiomeType = BIOME_DEFAULT_TYPE
    @Transient open val gradient: BiomeGradient = BIOME_DEFAULT_GRADIENT
    @Transient open val tileEntities: List<BiomeTileEntitiesConfig> = emptyList()
}