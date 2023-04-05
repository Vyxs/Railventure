package fr.manigames.railventure.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import fr.manigames.railventure.api.ecs.component.ComponentType
import fr.manigames.railventure.api.core.Metric.PHYSIC_PLAYER_ACCELERATION
import fr.manigames.railventure.api.ecs.entity.Entity
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.util.MathUtil.angleToNormalizedVector
import fr.manigames.railventure.api.ecs.world.World
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
            val left = Gdx.input.isKeyPressed(Keys.LEFT)
            val right = Gdx.input.isKeyPressed(Keys.RIGHT)
            val up = Gdx.input.isKeyPressed(Keys.UP)
            val down = Gdx.input.isKeyPressed(Keys.DOWN)
            val rotation = 90f
            val hasInput = (left || right || up || down)

            val angle = when {
                left && up -> 45f
                left && down -> 135f
                right && up -> 315f
                right && down -> 225f
                left -> 90f
                right -> 270f
                down -> 180f
                else -> 0f
            }

            world.updateComponents(entity, MoveableComponent(
                moveableComponent.speed,
                if (hasInput) (angle + rotation).angleToNormalizedVector() else moveableComponent.velocity,
                if (hasInput) PHYSIC_PLAYER_ACCELERATION else 0f,
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
