package fr.manigames.railventure.api.serialize.map

import fr.manigames.railventure.api.registry.Registry

class RegistryMapper {

    companion object {
        var nextId = 0
    }

    private val uidMap: MutableMap<String, Int> = HashMap()
    private val keyMap: MutableMap<Int, String> = HashMap()
    private var mapped = false

    operator fun get(key: String): Int = getUid(key)

    operator fun get(code: Int): String = getKey(code)

    private fun getUid(key: String): Int {
        if (!mapped) {
            throw IllegalStateException("You must call map() before using getUid()")
        }
        return uidMap[key] ?: -1
    }

    private fun getKey(uid: Int): String {
        if (!mapped) {
            throw IllegalStateException("You must call map() before using getKey()")
        }
        return keyMap[uid] ?: ""
    }

    fun map(registry: Registry<*>) {
        if (mapped) {
            throw IllegalStateException("You can only map a registry once")
        }
        registry.getAll().keys.sorted().forEach {
            val id = nextId++
            uidMap[it] = id
            keyMap[id] = it
        }
        mapped = true
    }
}