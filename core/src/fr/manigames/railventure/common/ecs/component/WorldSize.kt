package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class WorldSize(
    var width: Int = 1,
    var height: Int = 1,
    var offsetX: Float = 0f,
    var offsetY: Float = 0f,
    var ignoreHeight: Boolean = false,
    var ignoreWidth: Boolean = false
) : Component<WorldSize> {
    override fun type() = WorldSize
    companion object : ComponentType<WorldSize>()
}