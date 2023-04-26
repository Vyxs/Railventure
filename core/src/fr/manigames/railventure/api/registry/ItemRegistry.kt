package fr.manigames.railventure.api.registry

import fr.manigames.railventure.api.gameobject.item.Item

private class ItemAlreadyRegisteredException(message: String) : Exception(message)

class ItemRegistry : Registry<Item>() {

    /**
     * Register an item in the registry.
     *
     * @param registryObject The item to register.
     * @return The unique key associated with the registered item.
     * @throws ItemAlreadyRegisteredException If the item with the same key is already registered.
     */
    override fun register(registryObject: Item): String {
        if (registry.containsKey(registryObject.key)) {
            throw ItemAlreadyRegisteredException("Item with key '${registryObject.key}' is already registered.")
        }
        registry[registryObject.key] = registryObject
        return registryObject.key
    }
}