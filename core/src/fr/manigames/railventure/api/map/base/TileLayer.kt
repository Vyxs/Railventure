package fr.manigames.railventure.api.map.base

import fr.manigames.railventure.api.core.Metric.MAP_TILE_LAYER

@JvmInline
value class TileLayer(val layers: IntArray = IntArray(MAP_TILE_LAYER) { 0 }) {

    operator fun get(z: Int): Int {
        return layers[z]
    }

    operator fun set(z: Int, value: Int) {
        layers[z] = value
    }

    inline fun forEach(consumer: (Int) -> Unit) {
        layers.forEach(consumer)
    }

    inline fun forEachInverse(consumer: (Int) -> Unit) {
        layers.reversed().forEach(consumer)
    }

    inline fun first(predicate: (Int) -> Boolean): Int {
        return layers.first(predicate)
    }

    inline fun firstOrNull(predicate: (Int) -> Boolean): Int? {
        return layers.firstOrNull(predicate)
    }

    inline fun firstInverse(predicate: (Int) -> Boolean): Int {
        return layers.reversed().first(predicate)
    }

    inline fun firstInverseOrNull(predicate: (Int) -> Boolean): Int? {
        return layers.reversed().firstOrNull(predicate)
    }

    inline fun <R> map(transformer: (Int) -> R): List<R> {
        return layers.map(transformer)
    }

    operator fun iterator(): Iterator<Int> {
        return layers.iterator()
    }

    fun reversed(): IntArray {
        return layers.reversedArray()
    }

    companion object {

        fun empty() = TileLayer()

        fun of(vararg layers: Int) : TileLayer {
            return TileLayer(
                IntArray(MAP_TILE_LAYER) { layers.getOrNull(it) ?: 0 }
            )
        }
    }
}