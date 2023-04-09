package fr.manigames.railventure.common.ecs.component

import fr.manigames.railventure.api.ecs.component.Component
import fr.manigames.railventure.api.ecs.component.ComponentType

data class WorldSizeComponent(
    val width: Int = 1,
    val height: Int = 1,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    val ignoreHeight: Boolean = false,
    val ignoreWidth: Boolean = false
) : Component(ComponentType.WORLD_SIZE)