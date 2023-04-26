package fr.manigames.railventure.api.registry

import fr.manigames.railventure.api.gameobject.tile.Tile

private class TileAlreadyRegisteredException(message: String) : Exception(message)

class TileRegistry : Registry<Tile>() {

    /**
     * Register an item in the registry.
     *
     * @param registryObject The tile to register.
     * @return The unique key associated with the registered tile.
     * @throws TileAlreadyRegisteredException If the tile with the same key is already registered.
     */
    override fun register(registryObject: Tile): String {
        if (registry.containsKey(registryObject.key)) {
            throw TileAlreadyRegisteredException("Tile with key '${registryObject.key}' is already registered.")
        }
        registry[registryObject.key] = registryObject
        return registryObject.key
    }
}