package fr.manigames.railventure.api.gameobject.tile

import fr.manigames.railventure.api.registry.RegistryObject

abstract class Tile : RegistryObject {

    abstract override val key: String
    abstract val name: String
    abstract val texture: String
    @Transient open val isWalkable: Boolean = false
    @Transient open val isSpawnable: Boolean = false
}