package fr.manigames.railventure.api.map.biome

import fr.manigames.railventure.api.registry.RegistryObject

abstract class Biome : RegistryObject {
    companion object {
        val BIOME_DEFAULT_COLOR = 0xA020F0 // purple
    }

    abstract override val key: String
    abstract val name: String
    abstract val temperature: Int
    abstract val humidity: Int
    abstract val altitude: Int
    @Transient open val color: Int = 0xA020F0
    @Transient open val type: BiomeType = BiomeType.TERRESTRIAL
}