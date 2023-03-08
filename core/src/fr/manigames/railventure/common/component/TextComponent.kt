package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.graphics.font.Color

data class TextComponent(
    val text: String,
    val color: Color? = null
) : Component(ComponentType.TEXT)