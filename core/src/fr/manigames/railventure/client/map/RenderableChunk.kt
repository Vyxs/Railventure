package fr.manigames.railventure.client.map

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.TileLayer
import fr.manigames.railventure.common.map.BaseChunk
import java.util.concurrent.atomic.AtomicBoolean

open class RenderableChunk(x: Int, y: Int) : BaseChunk(x, y) {

    var texture: Texture? = null
        private set

    var isDirty = false
        private set

    var isLoading = AtomicBoolean(false)
        private set

    /**
     * Set the chunk as dirty. It should be marked as dirty when it has been modified.
     **/
    private fun markDirty() {
        isDirty = true
    }

    /**
     * Set the chunk as clean. It should be marked as clean when it has been loaded.
     **/
    private fun setClean() {
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
     * Load the chunk. If the chunk is already loading, it will not load it again.
     *
     * @param assets The assets manager
     **/
    fun load(assets: Assets) {
        if (isLoading.compareAndSet(false, true)) {
            generateChunkTexture(assets)
            setClean()
            isLoading.set(false)
        }
    }

    /**
     * Generate the chunk texture
     *
     * @param assets The assets manager
     **/
    private fun generateChunkTexture(assets: Assets) {
        val chunkSizeInPx = (Metric.MAP_CHUNK_SIZE * Metric.TILE_SIZE).toInt()
        val pixmap = Pixmap(chunkSizeInPx, chunkSizeInPx, Pixmap.Format.RGBA8888)
        getTiles().forEachIndexed { y, column ->
            column.forEachIndexed { x, tileLayer ->
                tileLayer.map(TileType::fromCode).forEach { tileType ->
                    assets.getTexture(tileType.assetKey)?.let {
                        if (!it.textureData.isPrepared)
                            it.textureData.prepare()
                        drawTextureToChunkPixmap(it, pixmap, x, y)
                    }
                }
            }
        }
        texture = Texture(pixmap)
        pixmap.dispose()
    }

    /**
     * Draw the texture to the chunk pixmap
     *
     * @param texture The texture to draw
     * @param pixmap The chunk pixmap to draw to
     * @param x The x world position
     * @param y The y world position
     **/
    private fun drawTextureToChunkPixmap(texture: Texture, pixmap: Pixmap, x: Int, y: Int) {
        val texturePixmap: Pixmap = if (texture.width != Metric.TILE_SIZE.toInt()) {
            getPixmapAtCorrectScale(texture)
        } else {
            getPixmap(texture)
        }
        for (i in 0 until texturePixmap.width) {
            for (j in 0 until texturePixmap.height) {
                val color = texturePixmap.getPixel(i, j)
                pixmap.drawPixel((x * Metric.TILE_SIZE + i).toInt(), (y * Metric.TILE_SIZE + j).toInt(), color)
            }
        }
    }

    /**
     * Get the pixmap at the correct scale
     *
     * @param texture The texture
     * @return The pixmap
     **/
    private fun getPixmapAtCorrectScale(texture: Texture): Pixmap {
        val pixmap = Pixmap(Metric.TILE_SIZE.toInt(), Metric.TILE_SIZE.toInt(), Pixmap.Format.RGBA8888)
        val texturePixmap = getPixmap(texture)
        pixmap.filter = Pixmap.Filter.NearestNeighbour
        pixmap.drawPixmap(texturePixmap, 0, 0, texturePixmap.width, texturePixmap.height, 0, 0, pixmap.width, pixmap.height)
        return pixmap
    }

    /**
     * Get the pixmap of the texture
     *
     * @param texture The texture
     * @return The pixmap
     **/
    private fun getPixmap(texture: Texture): Pixmap {
        if (!texture.textureData.isPrepared)
            texture.textureData.prepare()
        return texture.textureData.consumePixmap()
    }
}