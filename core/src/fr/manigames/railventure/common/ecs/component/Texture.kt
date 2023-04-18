package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class Texture(
    var texture: String
) : Component<Texture> {
    override fun type() = Texture
    companion object : ComponentType<Texture>()
}