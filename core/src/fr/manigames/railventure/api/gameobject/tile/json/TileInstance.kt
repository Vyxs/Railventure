package fr.manigames.railventure.api.gameobject.tile.json

import fr.manigames.railventure.api.gameobject.tile.Tile
import fr.manigames.railventure.api.serialize.json.Json

class InvalidTileModelException(message: String) : Exception(message)

class TileInstance(tileData: TileData) : Tile() {

    companion object {

        fun fromJsonModel(json: String?): Tile {
            return try {
                val tileData = Json().fromJson(json, TileData::class.java)
                TileInstance(tileData)
            } catch (e: Exception) {
                throw InvalidTileModelException("Invalid tile model: ${e.message}")
            }
        }
    }

    override val key: String = tileData.key
    override val name: String = tileData.name
    override val texture: String = tileData.texture
    override val isWalkable: Boolean = tileData.isWalkable
    override val isSpawnable: Boolean = tileData.isSpawnable
}