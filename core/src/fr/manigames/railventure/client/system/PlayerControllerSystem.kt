package fr.manigames.railventure.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
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
            var accel = 0f
            val left = Gdx.input.isKeyPressed(Keys.LEFT)
            val right = Gdx.input.isKeyPressed(Keys.RIGHT)
            val up = Gdx.input.isKeyPressed(Keys.UP)
            val down = Gdx.input.isKeyPressed(Keys.DOWN)

            if (left && up) {
                moveableComponent.velocity.x = -1f
                moveableComponent.velocity.y = 1f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (left && down) {
                moveableComponent.velocity.x = -1f
                moveableComponent.velocity.y = -1f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (right && up) {
                moveableComponent.velocity.x = 1f
                moveableComponent.velocity.y = 1f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (right && down) {
                moveableComponent.velocity.x = 1f
                moveableComponent.velocity.y = -1f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (left) {
                moveableComponent.velocity.x = -1f
                moveableComponent.velocity.y = 0f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (right) {
                moveableComponent.velocity.x = 1f
                moveableComponent.velocity.y = 0f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (up) {
                moveableComponent.velocity.x = 0f
                moveableComponent.velocity.y = 1f
                accel = PHYSIC_PLAYER_ACCELERATION
            } else if (down) {
                moveableComponent.velocity.x = 0f
                moveableComponent.velocity.y = -1f
                accel = PHYSIC_PLAYER_ACCELERATION
            }
            moveableComponent.acceleration = accel
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
