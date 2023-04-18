package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class HudPosition(
    var x: Float,
    var y: Float
) : Component<HudPosition> {
    override fun type() = HudPosition
    companion object : ComponentType<HudPosition>()
}