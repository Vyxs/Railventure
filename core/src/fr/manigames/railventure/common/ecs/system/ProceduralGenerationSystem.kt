package fr.manigames.railventure.common.ecs.system

import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.ecs.component.ComponentType
import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.*
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.map.ChunkLoader
import fr.manigames.railventure.common.ecs.component.WorldPositionComponent
import fr.manigames.railventure.common.generation.ProceduralHandler

class ProceduralGenerationSystem(
    world: World,
    private val map: ProceduralMap,
    private val handler: ProceduralHandler
) : System(world), ProceduralTileHandler {

    private val chunkLoader = ChunkLoader(Assets.instance)
    private val viewDistance = 4
    private val mapConfig = ProceduralMapConfig(
        seed = 4455,
        defaultGenerationSize = 20,
        regenerateOnConfigChange = false
    )
    override fun init() {
        map.setChunkLoader(chunkLoader::loadChunk)
        map.setProceduralMapConfig(mapConfig)
        map.setTileHandler(this)
        kotlin.run { map.generate() }
    }

    override fun update(delta: Float) {
        val chunkToGenerate = mutableListOf<Pair<Int, Int>>()

        world.getEntitiesWithComponents(ComponentType.PLAYER).forEach { entry ->
            val pos = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent
            val chunkPosition = PosUtil.getChunkPosition(pos.world_x, pos.world_y)
            val area = ChunkArea.fromCenter(chunkPosition.first, chunkPosition.second, viewDistance)

            area.toChunkPositions().forEach { chunkPos ->
                if (!map.hasChunk(chunkPos.first, chunkPos.second)) {
                    chunkToGenerate.add(chunkPos)
                }
            }
        }

        chunkToGenerate.forEach { chunkPos ->
            map.generateChunk(chunkPos.first, chunkPos.second)
        }
    }

    override fun determineTileType(
        altitude: Double,
        humidity: Double,
        temperature: Double,
        tileX: Int,
        tileY: Int
    ): TileType = handler.determineGameObjects(mapConfig.seed, world, altitude, humidity, temperature, tileX, tileY)
}