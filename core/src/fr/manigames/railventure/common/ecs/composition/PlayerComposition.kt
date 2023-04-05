package fr.manigames.railventure.common.ecs.composition

import fr.manigames.railventure.api.ecs.component.Component
import fr.manigames.railventure.api.ecs.component.Composition
import fr.manigames.railventure.common.ecs.component.MoveableComponent
import fr.manigames.railventure.common.ecs.component.PlayerComponent
import fr.manigames.railventure.common.ecs.component.TextureComponent
import fr.manigames.railventure.common.ecs.component.WorldPositionComponent
import fr.manigames.railventure.generated.R
import java.util.*

data class PlayerComposition(
    val player: PlayerComponent = PlayerComponent("player", UUID.randomUUID(), isReady = true, isHost = true),
    val worldPosition: WorldPositionComponent = WorldPositionComponent(0f, 0f),
    val texture: TextureComponent = TextureComponent(R.Texture.WAGON.path),
    val moveable: MoveableComponent = MoveableComponent()
) : Composition {

    override fun toComponents(): List<Component> {
        return listOf(player, worldPosition, texture, moveable)
    }
}