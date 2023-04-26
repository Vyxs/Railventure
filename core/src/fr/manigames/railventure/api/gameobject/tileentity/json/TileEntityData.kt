package fr.manigames.railventure.api.gameobject.tileentity.json

import fr.manigames.railventure.api.gameobject.tileentity.RenderType
import fr.manigames.railventure.api.gameobject.tileentity.SeasonWeatherTexture

data class TileEntityData(
    val key: String,
    val name: String,
    val texture: SeasonWeatherTexture,
    val renderType: RenderType = RenderType.TILE,
    val height: Float = 1f,
    val width: Float = 1f,
    val textureScale: Float = 1f,
    val isOrientable: Boolean = false,
    val isSolid: Boolean = false,
    val isHarvestable: Boolean = false
)