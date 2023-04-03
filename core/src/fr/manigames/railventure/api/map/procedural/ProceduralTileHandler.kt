package fr.manigames.railventure.api.map.procedural

import fr.manigames.railventure.api.gameobject.TileType

interface ProceduralTileHandler {

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
    fun determineTileType(altitude: Double, humidity: Double, temperature: Double, tileX: Int, tileY: Int): TileType
}