package fr.manigames.railventure.common.ecs.component

import fr.manigames.railventure.api.ecs.component.Component
import fr.manigames.railventure.api.ecs.component.ComponentType

data class HudPositionComponent(
    val x: Float,
    val y: Float
) : Component(ComponentType.HUD_POSITION)