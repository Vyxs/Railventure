package fr.manigames.railventure.client.map

import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.base.TileLayer
import fr.manigames.railventure.common.map.BaseChunk
import java.util.concurrent.atomic.AtomicBoolean

open class RenderableChunk(x: Int, y: Int) : BaseChunk(x, y) {

    var texture: Texture? = null

    var isDirty = false
        private set

    var isLoading = AtomicBoolean(false)
        private set

    /**
     * Set the chunk as dirty. It should be marked as dirty when it has been modified.
     **/
    fun markDirty() {
        isDirty = true
    }

    /**
     * Set the chunk as clean. It should be marked as clean when it has been loaded.
     **/
    fun setClean() {
        isDirty = false
    }

    /**
     * Set the tiles of the chunk and mark it as dirty.
     *
     * @param tiles The tiles of the chunk
     **/
    override fun setTiles(tiles: Array<Array<TileLayer>>) {
        super.setTiles(tiles)
        markDirty()
    }

    /**
     * Set the tile at the given position and mark it as dirty.
     *
     * @param x The x position
     * @param y The y position
     * @param z The z position depth
     * @param tileType The tile type
     **/
    override fun setTile(x: Int, y: Int, z: Int, tileType: TileType) {
        super.setTile(x, y, z, tileType)
        markDirty()
    }

    /**
     * Set the tile stack at the given position and mark it as dirty.
     *
     * @param x The x position
     * @param y The y position
     * @param stack The stack of tiles
     **/
    override fun setTileStack(x: Int, y: Int, stack: TileLayer) {
        super.setTileStack(x, y, stack)
        markDirty()
    }

    fun unload() {
        texture?.dispose()
        texture = null
    }

    fun isLoaded() = texture != null
}