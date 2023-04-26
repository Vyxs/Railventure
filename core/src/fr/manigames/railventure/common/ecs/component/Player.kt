package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import java.util.UUID

class Player(
    /**
     * The player's name
     **/
    var name: String,
    /**
     * The player's unique identifier
     **/
    var uuid: UUID,
    /**
     * If the player finished loading the game
     **/
    var isReady: Boolean,
    /**
     * If the player is the host of the server
     **/
    var isHost: Boolean
) : Component<Player> {
    override fun type() = Player
    companion object : ComponentType<Player>()
}