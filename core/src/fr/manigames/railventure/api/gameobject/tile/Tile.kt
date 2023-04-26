package fr.manigames.railventure.api.gameobject.tile

abstract class Tile {

    abstract val key: String
    abstract val name: String
    abstract val texture: String
    open val isWalkable: Boolean = false
    open val isSpawnable: Boolean = false
}