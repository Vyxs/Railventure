package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import fr.manigames.railventure.api.graphics.font.Color

class Text(
    var text: String,
    var color: Color? = null
) : Component<Text> {
    override fun type() = Text
    companion object : ComponentType<Text>()
}