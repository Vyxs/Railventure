package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteCache
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.core.Metric.TILE_SIZE
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.api.map.TileLayer
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.common.map.BaseChunk
import fr.manigames.railventure.common.map.BaseMap

class MapRenderer(
    private val map: BaseMap,
    private val asset: Assets,
    private val camera: Camera
) : Renderer {

    private val batch: SpriteBatch = SpriteBatch()
    private val tileArray: MutableSet<RenderTile> = mutableSetOf()
    var dirty = true

    fun render() {

        if (dirty)
            prepareChunkForRendering()

        batch.projectionMatrix = camera.combined
        batch.begin()
        for (tile in tileArray) {
            tile.texture?.let {
                batch.draw(it, tile.x, tile.y, TILE_SIZE, TILE_SIZE)
            }
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
                    prepareChunk(i, j)
                }
            }
        }
        dirty = false
    }

    private data class RenderTile(var texture: Texture?, var x: Float, var y: Float)

    private fun prepareChunk(chunkX: Int, chunkY: Int) {
        val chunk = map.getChunk(chunkX, chunkY)
        val tilesToRender:  Array<Array<TileLayer>> = chunk.getTiles()

        tilesToRender.forEachIndexed { y, column ->
            column.forEachIndexed { x, tileLayer ->
                tileLayer.map(TileType::fromCode).forEach { tileType ->
                    val texture = asset.getTexture(tileType.assetKey)
                    val pos = chunk.getTileWorldPosition(x, y)
                    val renderTile = RenderTile(texture, pos.first * TILE_SIZE, pos.second * TILE_SIZE)
                    tileArray.add(renderTile)
                }
            }
        }
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }

    override fun dispose() {
        batch.dispose()
    }
}