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

            move.speed += move.acceleration * (delta + PHYSIC_MIN_DELTA)
            move.speed = move.speed.coerceAtMost(move.maxSpeed)
            move.speed -= PHYSIC_FRICTION * (delta + PHYSIC_MIN_DELTA)
            move.speed = move.speed.coerceAtLeast(0f)
            val vec = move.velocity.cpy().nor().scl(move.speed)
            pos.world_x += vec.x
            pos.world_y += vec.y
        }
    }

}