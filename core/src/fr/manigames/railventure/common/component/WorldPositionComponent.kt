package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

data class WorldPositionComponent(
    var world_x: Float,
    var world_y: Float
) : Component(ComponentType.WORLD_POSITION)