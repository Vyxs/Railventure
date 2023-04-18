package fr.manigames.railventure.test

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import fr.manigames.railventure.client.renderer.DebugRenderer
import fr.manigames.railventure.common.ecs.component.Move
import fr.manigames.railventure.common.ecs.component.Player
import fr.manigames.railventure.common.ecs.component.WorldPosition

/**
 * Only for test purpose
 **/
class TestSystem(
    private val debugRenderer: DebugRenderer = inject(),
    private val cameraController: CameraController = inject(),
    private val useDebugCamera: Boolean = inject("useDebugCamera")
) : IteratingSystem(
    family { all(Player, WorldPosition, Move)}
) {
    override fun onTickEntity(entity: Entity) {
        val player = entity[Player]

        if (player.isHost) {
            debugRenderer.setMainPlayerData(
                entity[WorldPosition],
                entity[Move]
            )
            debugRenderer.worldEntityCount = world.numEntities
        }
    }

    override fun onUpdate() {
        if (useDebugCamera)
            cameraController.update(1f)
        debugRenderer.render()
        super.onUpdate()
    }
}