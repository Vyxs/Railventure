package fr.manigames.railventure.api.gameobject.tileentity.json

import fr.manigames.railventure.api.gameobject.tileentity.RenderType
import fr.manigames.railventure.api.gameobject.tileentity.SeasonWeatherTexture
import fr.manigames.railventure.api.gameobject.tileentity.TileEntity
import fr.manigames.railventure.api.serialize.json.Json

class TileEntityInstance(tileEntityData: TileEntityData) : TileEntity() {

    companion object {

        fun fromJsonModel(json: String?): TileEntity {
            val tileEntityData = Json().fromJson(json, TileEntityData::class.java)
            return TileEntityInstance(tileEntityData)
        }
    }

    override val key: String = tileEntityData.key
    override val name: String = tileEntityData.name
    override val texture: SeasonWeatherTexture = tileEntityData.texture
    override val renderType: RenderType = tileEntityData.renderType
    override val height: Float = tileEntityData.height
    override val width: Float = tileEntityData.width
    override val textureScale: Float = tileEntityData.textureScale
    override val isOrientable: Boolean = tileEntityData.isOrientable
    override val isSolid: Boolean = tileEntityData.isSolid
    override val isHarvestable: Boolean = tileEntityData.isHarvestable
}