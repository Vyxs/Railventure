package fr.manigames.railventure.common.ecs.component

import fr.manigames.railventure.api.ecs.component.Component
import fr.manigames.railventure.api.ecs.component.ComponentType
import java.util.UUID

data class PlayerComponent(
    /**
     * The player's name
     **/
    val name: String,
    /**
     * The player's unique identifier
     **/
    val uuid: UUID,
    /**
     * If the player finished loading the game
     **/
    val isReady: Boolean,
    /**
     * If the player is the host of the server
     **/
    val isHost: Boolean
) : Component(ComponentType.PLAYER)