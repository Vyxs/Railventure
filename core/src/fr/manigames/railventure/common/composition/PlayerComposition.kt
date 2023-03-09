package fr.manigames.railventure.common.composition

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.Composition
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.common.component.MoveableComponent
import fr.manigames.railventure.common.component.PlayerComponent
import fr.manigames.railventure.common.component.TextureComponent
import fr.manigames.railventure.common.component.WorldPositionComponent
import java.util.*

data class PlayerComposition(
    val player: PlayerComponent = PlayerComponent("player", UUID.randomUUID(), isReady = true, isHost = true),
    val worldPosition: WorldPositionComponent = WorldPositionComponent(0f, 0f),
    val texture: TextureComponent = TextureComponent(R.Texture.WAGON),
    val moveable: MoveableComponent = MoveableComponent()
) : Composition {

    override fun toComponents(): List<Component> {
        return listOf(player, worldPosition, texture, moveable)
    }
}