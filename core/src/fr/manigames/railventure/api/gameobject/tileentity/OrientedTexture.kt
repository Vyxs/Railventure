package fr.manigames.railventure.api.gameobject.tileentity

data class OrientedTexture(
    val base: String,
    val north: String = base,
    val east: String = base,
    val south: String = base,
    val west: String = base
)