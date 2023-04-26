package fr.manigames.railventure.client.system

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Metric.CAMERA_ZOOM
import fr.manigames.railventure.common.ecs.component.Player
import fr.manigames.railventure.common.ecs.component.WorldPosition

class PlayerCameraSystem(
    private val camera: Camera = inject()
) : IteratingSystem(
    family { all(Player, WorldPosition) }
) {

    init {
        if (camera is OrthographicCamera)
            camera.zoom = CAMERA_ZOOM
    }
    override fun onTickEntity(entity: Entity) {
        val player = entity[Player]
        val position = entity[WorldPosition]

        if (player.isHost) {
            camera.position.x = position.world_x * Metric.TILE_SIZE + Metric.TILE_SIZE / 2
            camera.position.y = position.world_y * Metric.TILE_SIZE + Metric.TILE_SIZE / 2
            camera.update()
        }
    }
}
