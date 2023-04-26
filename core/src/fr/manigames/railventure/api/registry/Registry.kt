package fr.manigames.railventure.api.registry

abstract class Registry<T> {

    protected val registry: MutableMap<String, T> = HashMap()

    abstract fun register(registryObject: T): String

    fun get(key: String): T? {
        return registry[key]
    }

    fun remove(key: String): T? {
        return registry.remove(key)
    }

    fun set(key: String, item: T) {
        registry[key] = item
    }

    fun getAll(): Map<String, T> {
        return registry
    }

    fun clear() {
        registry.clear()
    }
}