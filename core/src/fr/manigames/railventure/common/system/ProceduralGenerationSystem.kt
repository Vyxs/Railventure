package fr.manigames.railventure.common.system

import fr.manigames.railventure.api.ecs.component.ComponentType
import fr.manigames.railventure.api.map.generation.ProceduralMap
import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.common.component.WorldPositionComponent

class ProceduralGenerationSystem(
    world: World,
    private val map: ProceduralMap
) : System(world) {

    private val viewDistance = 4

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
}