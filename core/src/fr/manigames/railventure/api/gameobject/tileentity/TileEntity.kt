package fr.manigames.railventure.api.gameobject.tileentity

abstract class TileEntity {

    abstract val key: String
    abstract val name: String
    abstract val texture: SeasonWeatherTexture
    @Transient open val renderType: RenderType = RenderType.TILE
    @Transient open val height: Float = 1f
    @Transient open val width: Float = 1f
    @Transient open val textureScale: Float = 1f
    @Transient open val isOrientable: Boolean = false
    @Transient open val isSolid: Boolean = false
    @Transient open val isHarvestable: Boolean = false
}