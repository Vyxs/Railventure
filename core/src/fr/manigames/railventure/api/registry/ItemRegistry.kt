package fr.manigames.railventure.api.registry

import fr.manigames.railventure.api.item.Item

private class ItemAlreadyRegisteredException(message: String) : Exception(message)

class ItemRegistry : Registry<Item>() {

    /**
     * Register an item in the registry.
     *
     * @param item The item to register.
     * @return The unique key associated with the registered item.
     * @throws ItemAlreadyRegisteredException If the item with the same key or ID is already registered.
     */
    override fun register(registryObject: Item): String {
        if (registry.containsKey(registryObject.key) || registry.values.any { it.id == registryObject.id }) {
            throw ItemAlreadyRegisteredException("Item with key '${registryObject.key}' or ID '${registryObject.id}' is already registered.")
        }
        registry[registryObject.key] = registryObject
        return registryObject.key
    }

}