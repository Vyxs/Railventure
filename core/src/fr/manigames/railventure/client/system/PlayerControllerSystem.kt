package fr.manigames.railventure.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import fr.manigames.railventure.api.core.Metric.PHYSIC_PLAYER_ACCELERATION
import fr.manigames.railventure.api.util.MathUtil.angleToNormalizedVector
import fr.manigames.railventure.common.ecs.component.Move
import fr.manigames.railventure.common.ecs.component.Player

class PlayerControllerSystem : IteratingSystem(
    family { all(Player, Move) }
) {
    override fun onTickEntity(entity: Entity) {
        val player = entity[Player]

        if (player.isHost)
            handleInput(entity)
    }

    private fun handleInput(entity: Entity) {
        val move = entity[Move]
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

        move.velocity = if (hasInput) (angle + rotation).angleToNormalizedVector() else move.velocity
        move.acceleration = if (hasInput) PHYSIC_PLAYER_ACCELERATION else 0f
    }
}

