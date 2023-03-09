package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

data class HudPositionComponent(
    val x: Float,
    val y: Float
) : Component(ComponentType.HUD_POSITION)