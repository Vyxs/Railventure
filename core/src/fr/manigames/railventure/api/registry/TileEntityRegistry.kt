package fr.manigames.railventure.api.registry

import fr.manigames.railventure.api.gameobject.tileentity.TileEntity

private class TileEntityAlreadyRegisteredException(message: String) : Exception(message)

class TileEntityRegistry : Registry<TileEntity>() {

/**
     * Register an tileEntity in the registry.
     *
     * @param registryObject The tileEntity to register.
     * @return The unique key associated with the registered tileEntity.
     * @throws TileEntityAlreadyRegisteredException If the tileEntity with the same key is already registered.
     */
    override fun register(registryObject: TileEntity): String {
        if (registry.containsKey(registryObject.key)) {
            throw TileEntityAlreadyRegisteredException("TileEntity with key '${registryObject.key}' is already registered.")
        }
        registry[registryObject.key] = registryObject
        return registryObject.key
    }
}