package fr.manigames.railventure.common.system

import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.MoveableComponent
import fr.manigames.railventure.common.component.WorldPositionComponent

class PhysicSystem(world: World) : System(world) {

    private val friction = 0.9f

    override fun update(delta: Float) {

        world.getEntitiesWithComponents(ComponentType.MOVEABLE, ComponentType.WORLD_POSITION).forEach { entry ->
            val moveableComponent = entry.value.first { it.componentType == ComponentType.MOVEABLE } as MoveableComponent
            val worldPositionComponent = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent



        }
    }

}