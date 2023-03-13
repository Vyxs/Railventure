package fr.manigames.railventure.common.system

import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.core.Metric.PHYSIC_FRICTION
import fr.manigames.railventure.api.core.Metric.PHYSIC_MIN_DELTA
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.MoveableComponent
import fr.manigames.railventure.common.component.WorldPositionComponent

class PhysicSystem(world: World) : System(world) {

    override fun update(delta: Float) {

        world.getEntitiesWithComponents(ComponentType.MOVEABLE, ComponentType.WORLD_POSITION).forEach { entry ->
            val move = entry.value.first { it.componentType == ComponentType.MOVEABLE } as MoveableComponent
            val pos = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent

            var speed = move.speed
            speed += move.acceleration * (delta + PHYSIC_MIN_DELTA)
            speed = speed.coerceAtMost(move.maxSpeed)
            speed -= PHYSIC_FRICTION * (delta + PHYSIC_MIN_DELTA)
            speed = speed.coerceAtLeast(0f)
            val vec = move.velocity.cpy().nor().scl(move.speed)

            world.updateComponents(entry.key,
                MoveableComponent(
                    speed,
                    move.velocity,
                    move.acceleration,
                    move.orientation,
                    move.angularSpeed,
                    move.angularAcceleration,
                    move.maxSpeed,
                    move.maxAngularSpeed
                ),
                WorldPositionComponent(pos.world_x + vec.x, pos.world_y + vec.y)
            )
        }
    }

}