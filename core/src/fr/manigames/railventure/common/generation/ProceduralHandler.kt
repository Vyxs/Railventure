package fr.manigames.railventure.common.generation

import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.api.gameobject.TileType

interface ProceduralHandler {

    fun determineGameObjects(seed: Long, world: World, altitude: Double, humidity: Double, temperature: Double, tileX: Int, tileY: Int): TileType

}