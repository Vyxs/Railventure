package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.common.map.BaseMap

class MapRenderer(
    private val map: RenderableMap,
    private val camera: Camera
) : Renderer {

    private val batch: SpriteBatch = SpriteBatch()
    private var visibleChunks: MutableMap<Long, RenderableChunk> = mutableMapOf()
    private val chunkSizeInPx = (Metric.TILE_SIZE * Metric.MAP_CHUNK_SIZE).toInt()
    private var firstInit = true
    private var lastCameraChunkPosition = Pair(0, 0)

    fun render() {
        if (firstInit) {
            firstInit = false
            updateCameraChunkPosition()
            prepareChunkForRendering()
        }
        batch.projectionMatrix = camera.combined
        batch.begin()
        visibleChunks.forEach { (_, chunk) ->
            chunk.texture?.let {
                batch.draw(it, chunk.x.toFloat() * chunkSizeInPx, chunk.y.toFloat() * chunkSizeInPx, chunkSizeInPx.toFloat(), chunkSizeInPx.toFloat())
            }
        }
        batch.end()
    }

    fun update() {
        checkDirty()
        updateCameraChunkPosition()
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
                    if (visibleChunks.containsKey(BaseMap.toChunkId(i, j)).not()) {
                        if (map.isChunkDirty(i, j)) {
                            map.loadChunk(i, j)
                        }
                        visibleChunks[BaseMap.toChunkId(i, j)] = map.getChunk(i, j) as RenderableChunk
                    }
                }
            }
        }
    }

    private fun updateCameraChunkPosition() {
        if (camera is OrthographicCamera) {
            val worldPos = PosUtil.getWorldPosition(camera.position.x, camera.position.y)
            val chunkPos = PosUtil.getChunkPosition(worldPos.first, worldPos.second)
            if (chunkPos != lastCameraChunkPosition) {
                lastCameraChunkPosition = chunkPos
                prepareChunkForRendering()
            }
        }
    }

    private fun checkDirty() {
        visibleChunks.forEach { (_, chunk) ->
            if (chunk.isDirty) {
                map.loadChunk(chunk.x, chunk.y)
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