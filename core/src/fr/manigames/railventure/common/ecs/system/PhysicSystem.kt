package fr.manigames.railventure.common.ecs.system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import fr.manigames.railventure.api.core.Metric.PHYSIC_FRICTION
import fr.manigames.railventure.api.core.Metric.PHYSIC_MIN_DELTA
import fr.manigames.railventure.common.ecs.component.Move
import fr.manigames.railventure.common.ecs.component.WorldPosition

class PhysicSystem : IteratingSystem(
    family { all(Move, WorldPosition)}
) {

    override fun onTickEntity(entity: Entity) {
        val move = entity[Move]
        val pos = entity[WorldPosition]

        var speed = move.speed
        speed += move.acceleration * (deltaTime + PHYSIC_MIN_DELTA)
        speed = speed.coerceAtMost(move.maxSpeed)
        speed -= PHYSIC_FRICTION * (deltaTime + PHYSIC_MIN_DELTA)
        speed = speed.coerceAtLeast(0f)
        val vec = move.velocity.cpy().nor().scl(move.speed)

        move.speed = speed
        pos.world_x += vec.x
        pos.world_y += vec.y
    }
}