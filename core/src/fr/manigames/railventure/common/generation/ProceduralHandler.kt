package fr.manigames.railventure.common.generation

import com.github.quillraven.fleks.World
import fr.manigames.railventure.api.gameobject.TileType

interface ProceduralHandler {

    fun determineGameObjects(seed: Long, world: World, altitude: Double, humidity: Double, temperature: Double, tileX: Int, tileY: Int): TileType

}