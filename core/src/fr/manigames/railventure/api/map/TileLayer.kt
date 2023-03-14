package fr.manigames.railventure.api.map

import fr.manigames.railventure.api.core.Metric.MAP_TILE_LAYER

@JvmInline
value class TileLayer(private val layers: Array<Int> = Array(MAP_TILE_LAYER) { 0 }) {

    operator fun get(z: Int): Int {
        return layers[z]
    }

    operator fun set(z: Int, value: Int) {
        layers[z] = value
    }

    companion object {

        fun empty() = TileLayer()

        fun of(vararg layers: Int) : TileLayer {
            return TileLayer(
                Array(MAP_TILE_LAYER) { layers.getOrNull(it) ?: 0 }
            )
        }
    }
}