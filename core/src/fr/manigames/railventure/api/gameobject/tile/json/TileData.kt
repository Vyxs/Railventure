package fr.manigames.railventure.api.gameobject.tile.json

data class TileData(
    val key: String,
    val name: String,
    val texture: String,
    val isWalkable: Boolean = false,
    val isSpawnable: Boolean = false
)