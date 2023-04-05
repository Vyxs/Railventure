package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.ecs.component.Component
import fr.manigames.railventure.api.ecs.component.ComponentType

data class TextureComponent(
    val texture: String
): Component(ComponentType.TEXTURE)
