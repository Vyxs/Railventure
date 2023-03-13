package fr.manigames.railventure.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.core.Metric.PHYSIC_PLAYER_ACCELERATION
import fr.manigames.railventure.api.entity.Entity
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.MoveableComponent
import fr.manigames.railventure.common.component.PlayerComponent

class PlayerControllerSystem(world: World) : System(world) {

    private var player: Entity? = null

    override fun init() {
        findPlayer()
    }

    override fun update(delta: Float) {
        if (player == null)
            findPlayer()
        if (player != null)
            handleInput()
    }

    private fun handleInput() {
        player?.let { entity ->
            val moveableComponent = world.getComponent<MoveableComponent>(entity, ComponentType.MOVEABLE)
            var accelerate = false
            val velocity = moveableComponent.velocity.cpy()
            val left = Gdx.input.isKeyPressed(Keys.LEFT)
            val right = Gdx.input.isKeyPressed(Keys.RIGHT)
            val up = Gdx.input.isKeyPressed(Keys.UP)
            val down = Gdx.input.isKeyPressed(Keys.DOWN)

            if (left && up) {
                velocity.x = -1f
                velocity.y = 1f
                accelerate = true
            } else if (left && down) {
                velocity.x = -1f
                velocity.y = -1f
                accelerate = true
            } else if (right && up) {
                velocity.x = 1f
                velocity.y = 1f
                accelerate = true
            } else if (right && down) {
                velocity.x = 1f
                velocity.y = -1f
                accelerate = true
            } else if (left) {
                velocity.x = -1f
                velocity.y = 0f
                accelerate = true
            } else if (right) {
                velocity.x = 1f
                velocity.y = 0f
                accelerate = true
            } else if (up) {
                velocity.x = 0f
                velocity.y = 1f
                accelerate = true
            } else if (down) {
                velocity.x = 0f
                velocity.y = -1f
                accelerate = true
            }

            world.updateComponents(entity, MoveableComponent(
                moveableComponent.speed,
                velocity,
                if (accelerate) PHYSIC_PLAYER_ACCELERATION else 0f,
                moveableComponent.orientation,
                moveableComponent.angularSpeed,
                moveableComponent.angularAcceleration,
                moveableComponent.maxSpeed,
                moveableComponent.maxAngularSpeed
            ))
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
