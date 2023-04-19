package fr.manigames.railventure.api.map.generation

import fr.manigames.railventure.api.map.base.TileLayer

interface ProceduralTileHandler {
    object DEFAULT : ProceduralTileHandler {
        override fun determineTileLayer(alt: Double, hum: Double, temp: Double, ux: Int, uy: Int): TileLayer {
            return TileLayer.empty()
        }
    }

    /**
     * Determine the tile type from the given altitude, humidity and temperature
     *
     * @param altitude The altitude of the tile
     * @param humidity The humidity of the tile
     * @param temperature The temperature of the tile
     * @param tileX The x position of the tile in the world (unscaled)
     * @param tileY The y position of the tile in the world (unscaled)
     * @return The tile type
     */
    fun determineTileLayer(alt: Double, hum: Double, temp: Double, ux: Int, uy: Int): TileLayer
}