package fr.manigames.railventure.client.system

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Metric.CAMERA_ZOOM
import fr.manigames.railventure.api.entity.Entity
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.PlayerComponent
import fr.manigames.railventure.common.component.WorldPositionComponent

class PlayerCameraSystem(
    world: World,
    private val camera: Camera
) : System(world) {

    private var player: Entity? = null

    override fun init() {
        if (camera is OrthographicCamera)
            camera.zoom = CAMERA_ZOOM
        findPlayer()
    }

    override fun update(delta: Float) {
        if (player == null)
            findPlayer()
        if (player != null)
            handleCamera()
        camera.update()
    }

    private fun handleCamera() {
        player?.let {
            world.getComponent<WorldPositionComponent>(it, ComponentType.WORLD_POSITION).let { posComponent ->
                camera.position.x = posComponent.world_x * Metric.TILE_SIZE + Metric.TILE_SIZE / 2
                camera.position.y = posComponent.world_y * Metric.TILE_SIZE + Metric.TILE_SIZE / 2
            }
        }
    }

    private fun findPlayer() {
        world.getEntitiesWithComponents(ComponentType.PLAYER).forEach { entry ->
            if ((entry.value.first { it.componentType == ComponentType.PLAYER } as PlayerComponent).isHost) {
                player = entry.key
            }
        }
    }
}