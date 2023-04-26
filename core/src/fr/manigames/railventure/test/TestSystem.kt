package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import fr.manigames.railventure.client.input.GameInput
import fr.manigames.railventure.client.renderer.DebugRenderer
import fr.manigames.railventure.common.ecs.component.Move
import fr.manigames.railventure.common.ecs.component.Player
import fr.manigames.railventure.common.ecs.component.WorldPosition

/**
 * Only for test purpose
 **/
class TestSystem(
    camera: Camera = inject(),
    gameInput: GameInput = inject(),
    private val debugRenderer: DebugRenderer = inject(),
    private val useDebugCamera: Boolean = inject("useDebugCamera"),
    private val useFreeCamera: Boolean = inject("useFreeCamera")
) : IteratingSystem(
    family { all(Player, WorldPosition, Move)}
) {

    private val cameraController: CameraController = CameraController(camera)
    private val freeCamera3D: CameraInputController = CameraInputController(camera)

    init {
        gameInput.addInputProcessor(debugRenderer.inputProcessor)
        if (useDebugCamera) {
            if (useFreeCamera) {
                gameInput.addInputProcessor(freeCamera3D)
            } else {
                gameInput.addInputProcessor(cameraController)
                cameraController.init()
            }
        }
    }
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
        if (useDebugCamera) {
            if (useFreeCamera) {
                freeCamera3D.update()
            } else {
                cameraController.update(deltaTime)
            }
        }
        debugRenderer.render()
        super.onUpdate()
    }
}