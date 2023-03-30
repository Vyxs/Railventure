package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.core.Metric.TILE_SIZE
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.api.map.TileLayer
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.common.map.BaseMap

/**
 * Better than MapRenderer but not clean. Use only for debug purpose.
 **/
class FastMapRenderer(
    private val map: BaseMap,
    private val asset: Assets,
    private val camera: Camera
) : Renderer {

    private val batch = SpriteBatch()
    private val chunkRealSize = (TILE_SIZE * MAP_CHUNK_SIZE).toInt()
    private var chunks: MutableMap<Long, Texture> = mutableMapOf()
    private var dirty = true

    fun render() {
        val size = (TILE_SIZE * MAP_CHUNK_SIZE).toInt()

        if (dirty) {
            prepareChunkForRendering()
            dirty = false
        }
        batch.projectionMatrix = camera.combined
        batch.begin()
        chunks.forEach { (id, texture) ->
            val chunkPos = BaseMap.fromChunkId(id)
            batch.draw(texture, chunkPos.first * size.toFloat(), chunkPos.second * size.toFloat(), size.toFloat(), size.toFloat())
        }
        batch.end()
    }

    private fun prepareChunkForRendering() {
        if (camera is OrthographicCamera) {
            val offset = 0
            val horizontalChunkCount = PosUtil.getChunkVisibleHorizontal(camera.position.x, camera.viewportWidth, camera.zoom) + offset
            val verticalChunkCount = PosUtil.getChunkVisibleVertical(camera.position.y, camera.viewportHeight, camera.zoom) + offset


            val worldPos = PosUtil.getWorldPosition(camera.position.x, camera.position.y)
            val chunkPos = PosUtil.getChunkPosition(worldPos.first, worldPos.second)

            for (i in chunkPos.first - horizontalChunkCount..chunkPos.first + horizontalChunkCount) {
                for (j in chunkPos.second - verticalChunkCount..chunkPos.second + verticalChunkCount) {
                    chunks[BaseMap.toChunkId(i, j)] = Texture(prepareChunk(i, j))
                }
            }
        }
    }

    private fun prepareChunk(chunkX: Int, chunkY: Int) : Pixmap {
        val chunk = map.getChunk(chunkX, chunkY)
        val tilesToRender: Array<Array<TileLayer>>? = chunk?.getTiles()
        val chunkPixmap = Pixmap(chunkRealSize, chunkRealSize, Pixmap.Format.RGBA8888)

        tilesToRender?.forEachIndexed { y, column ->
            column.forEachIndexed { x, tileLayer ->
                tileLayer.map(TileType::fromCode).forEach { tileType ->
                    asset.getTexture(tileType.texture.path)?.let {
                        drawTextureToChunkPixmap(it, chunkPixmap, x, y)
                    }
                }
            }
        }
        return chunkPixmap
    }

    private fun drawTextureToChunkPixmap(texture: Texture, pixmap: Pixmap, x: Int, y: Int) {
        val texturePixmap: Pixmap = if (texture.width != TILE_SIZE.toInt()) {
            getPixmapAtCorrectScale(texture)
        } else {
            getPixmap(texture)
        }
        for (i in 0 until texturePixmap.width) {
            for (j in 0 until texturePixmap.height) {
                val color = texturePixmap.getPixel(i, j)
                pixmap.drawPixel((x * TILE_SIZE + i).toInt(), (y * TILE_SIZE + j).toInt(), color)
            }
        }
    }

    private fun getPixmapAtCorrectScale(texture: Texture): Pixmap {
        val pixmap = Pixmap(TILE_SIZE.toInt(), TILE_SIZE.toInt(), Pixmap.Format.RGBA8888)
        val texturePixmap = getPixmap(texture)
        pixmap.filter = Pixmap.Filter.NearestNeighbour
        pixmap.drawPixmap(texturePixmap, 0, 0, texturePixmap.width, texturePixmap.height, 0, 0, pixmap.width, pixmap.height)
        return pixmap
    }

    private fun getPixmap(texture: Texture): Pixmap {
        if (texture.textureData.isPrepared.not())
            texture.textureData.prepare()
        return texture.textureData.consumePixmap()
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }

    override fun dispose() {
        batch.dispose()
    }
}