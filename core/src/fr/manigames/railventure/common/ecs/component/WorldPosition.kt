package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class WorldPosition(
    var world_x: Float,
    var world_y: Float
) : Component<WorldPosition> {
    override fun type() = WorldPosition
    companion object : ComponentType<WorldPosition>()
}