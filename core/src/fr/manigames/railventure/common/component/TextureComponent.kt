package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

data class TextureComponent(
    val texture: String
): Component(ComponentType.TEXTURE)
