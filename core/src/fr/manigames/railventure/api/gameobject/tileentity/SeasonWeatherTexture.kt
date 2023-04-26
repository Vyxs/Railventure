package fr.manigames.railventure.api.gameobject.tileentity

data class SeasonWeatherTexture(
    val base: OrientedTexture,
    val spring: OrientedTexture = base,
    val summer: OrientedTexture = base,
    val autumn: OrientedTexture = base,
    val winter: OrientedTexture = base
)