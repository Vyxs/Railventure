package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.ecs.component.Component
import fr.manigames.railventure.api.ecs.component.ComponentType

data class WorldPositionComponent(
    val world_x: Float,
    val world_y: Float
) : Component(ComponentType.WORLD_POSITION)