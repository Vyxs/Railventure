package fr.manigames.railventure.client.system

import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.entity.Entity
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.PlayerComponent

class PlayerControllerSystem(world: World) : System(world) {

    private var player: Entity? = null

    override fun init() {
        findPlayer()
    }

    override fun update(delta: Float) {
        if (player == null)
            findPlayer()
        if (player != null)
            handleInput(delta)
    }

    private fun handleInput(delta: Float) {

    }

    private fun findPlayer() {
        world.getEntitiesWithComponents(ComponentType.PLAYER).forEach { entry ->
            if ((entry.value.first { it.componentType == ComponentType.PLAYER } as PlayerComponent).isHost) {
                player = entry.key
            }
        }
    }
}
