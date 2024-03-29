package fr.manigames.railventure.client.map

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.map.base.ChunkLoader
import fr.manigames.railventure.api.registry.TileRegistry

open class ChunkLoader(
    private val assets: Assets,
    private val tileRegistry: TileRegistry
) : ChunkLoader {

    private val mutableMap = mutableMapOf<Int, Pixmap>()

    override fun loadChunk(chunk: RenderableChunk) {
        if (chunk.isLoading.compareAndSet(false, true)) {
            generateChunkTexture(chunk, assets)
            chunk.isLoading.set(false)
        }
    }

    /**
     * Generate the chunk texture
     *
     * @param assets The assets manager
     **/
    protected open fun generateChunkTexture(chunk: RenderableChunk, assets: Assets) {
        val pixmap = makePixmap(chunk, assets)
        applyPixmapToChunk(chunk, pixmap)
    }

    /**
     * Cache the texture and dispose pixmap.
     *
     * @param chunk The chunk to cache the texture
     * @param pixmap The pixmap to create the texture
     **/
    protected open fun applyPixmapToChunk(chunk: RenderableChunk, pixmap: Pixmap) {
        chunk.texture = Texture(pixmap)
        pixmap.dispose()
    }

    /**
     * Make the chunk pixmap
     *
     * @param chunk The chunk to make the pixmap from
     * @param assets The assets manager
     * @return The chunk pixmap
     **/
    protected open fun makePixmap(chunk: RenderableChunk, assets: Assets) : Pixmap {
        val chunkSizeInPx = (Metric.MAP_CHUNK_SIZE * Metric.TILE_SIZE).toInt()
        val pixmap = Pixmap(chunkSizeInPx, chunkSizeInPx, Pixmap.Format.RGBA8888)

        chunk.getTiles().reversed().forEachIndexed { y, column ->

            column.forEachIndexed { x, tileLayer ->

                tileLayer[Metric.MAP_GROUND_LAYER].let { code ->
                    val key = tileRegistry.mapper[code]
                    val tile = tileRegistry[key]


                    tile?.texture?.let { texture ->

                        assets.getTexture(texture)?.let {
                            drawTextureToChunkPixmap(code, it, pixmap, x, y)
                        }
                    }
                }
            }
        }
        return pixmap
    }

    /**
     * Draw the texture to the chunk pixmap
     *
     * @param texture The texture to draw
     * @param pixmap The chunk pixmap to draw to
     * @param x The x world position
     * @param y The y world position
     **/
    private fun drawTextureToChunkPixmap(type: Int, texture: Texture, pixmap: Pixmap, x: Int, y: Int) {
        cacheTexture(type, texture)
        pixmap.drawPixmap(mutableMap[type], x * Metric.TILE_SIZE.toInt(), y * Metric.TILE_SIZE.toInt())
    }

    /**
     * Cache the texture
     *
     * @param type The tile type
     * @param texture The texture to cache
     **/
    private fun cacheTexture(type: Int, texture: Texture) {
        if (mutableMap[type] == null) {
            mutableMap[type] = if (texture.width != Metric.TILE_SIZE.toInt()) {
                getPixmapAtCorrectScale(texture)
            } else {
                getPixmap(texture)
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