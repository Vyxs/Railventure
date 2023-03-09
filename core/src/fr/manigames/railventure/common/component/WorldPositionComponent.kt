package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

data class WorldPositionComponent(
    val world_x: Int,
    val world_y: Int
) : Component(ComponentType.WORLD_POSITION)