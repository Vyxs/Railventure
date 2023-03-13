package fr.manigames.railventure.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.core.Metric.PHYSIC_PLAYER_ACCELERATION
import fr.manigames.railventure.api.entity.Entity
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.util.MathUtil.angleToNormalizedVector
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
            var velocity = moveableComponent.velocity.cpy()
            val left = Gdx.input.isKeyPressed(Keys.LEFT)
            val right = Gdx.input.isKeyPressed(Keys.RIGHT)
            val up = Gdx.input.isKeyPressed(Keys.UP)
            val down = Gdx.input.isKeyPressed(Keys.DOWN)
            val rotation = 90f

            if (left && up) {
                velocity = (45 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (left && down) {
                velocity = (135 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (right && up) {
                velocity = (315 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (right && down) {
                velocity = (225 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (left) {
                velocity = (90 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (right) {
                velocity = (270 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (up) {
                velocity = (0 + rotation).angleToNormalizedVector()
                accelerate = true
            } else if (down) {
                velocity = (180 + rotation).angleToNormalizedVector()
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
