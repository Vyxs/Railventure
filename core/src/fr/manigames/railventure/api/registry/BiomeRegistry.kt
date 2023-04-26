package fr.manigames.railventure.api.registry

import fr.manigames.railventure.api.map.biome.Biome

private class BiomeAlreadyRegisteredException(message: String) : Exception(message)

class BiomeRegistry : Registry<Biome>() {

    /**
     * Register a biome in the registry.
     *
     * @param registryObject The biome to register.
     * @return The unique key associated with the registered biome.
     * @throws ItemAlreadyRegisteredException If the biome with the same key is already registered.
     */
    override fun register(registryObject: Biome): String {
        if (registry.containsKey(registryObject.key)) {
            throw BiomeAlreadyRegisteredException("Biome with key '${registryObject.key}' is already registered.")
        }
        registry[registryObject.key] = registryObject
        return registryObject.key
    }
}