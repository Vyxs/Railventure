package fr.manigames.railventure.common.ecs.system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.map.base.TileLayer
import fr.manigames.railventure.api.map.generation.*
import fr.manigames.railventure.api.registry.BiomeRegistry
import fr.manigames.railventure.api.registry.TileRegistry
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.map.ChunkLoader
import fr.manigames.railventure.common.ecs.component.Player
import fr.manigames.railventure.common.ecs.component.WorldPosition
import fr.manigames.railventure.test.ProceduralHandler


class ProceduralGenerationSystem(
    private val map: ProceduralMap = inject(),
    biomeRegistry: BiomeRegistry = inject(),
    tileRegistry: TileRegistry = inject()
) : IteratingSystem(
    family { all(Player, WorldPosition)}
), ProceduralTileHandler {

    private val chunkLoader = ChunkLoader(Assets.instance, tileRegistry)
    private val viewDistance = 4
    private val mapConfig = ProceduralMapConfig(
        seed = 4455,
        defaultGenerationSize = 20,
        regenerateOnConfigChange = false
    )
    private val handler: ProceduralHandler = ProceduralHandler(mapConfig.seed, biomeRegistry, tileRegistry)

    init {
        map.setChunkLoader(chunkLoader::loadChunk)
        map.setProceduralMapConfig(mapConfig)
        map.setTileHandler(this)
        kotlin.run { map.generate() }
    }

    override fun onTickEntity(entity: Entity) {
        val chunkToGenerate = mutableListOf<Pair<Int, Int>>()

        val pos = entity[WorldPosition]
        val chunkPosition = PosUtil.getChunkPosition(pos.world_x, pos.world_y)
        val area = ChunkArea.fromCenter(chunkPosition.x, chunkPosition.y, viewDistance)

        area.toChunkPositions().forEach { chunkPos ->
            if (!map.hasChunk(chunkPos.first, chunkPos.second)) {
                chunkToGenerate.add(chunkPos)
            }
        }
        chunkToGenerate.forEach { chunkPos ->
            map.generateChunk(chunkPos.first, chunkPos.second)
        }
    }

    override fun determineTileLayer(alt: Double, hum: Double, temp: Double, ux: Int, uy: Int): TileLayer =
        handler.determineGameObjects(mapConfig.seed, world, alt, hum, temp, ux, uy)
}