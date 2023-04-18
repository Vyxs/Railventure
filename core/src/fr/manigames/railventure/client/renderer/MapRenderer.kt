package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Metric.CAMERA_HEIGHT_MAX
import fr.manigames.railventure.api.core.Metric.CAMERA_HEIGHT_MIN
import fr.manigames.railventure.api.core.Metric.CAMERA_ZOOM_MAX
import fr.manigames.railventure.api.core.Metric.CAMERA_ZOOM_MIN
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.CameraUtil.normalizeZ
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.common.map.BaseMap

class MapRenderer(
    private val map: RenderableMap,
    private val camera: Camera
) : Renderer {

    private val batch: SpriteBatch = Render.spriteBatch
    private var visibleChunks: MutableMap<Long, RenderableChunk> = mutableMapOf()
    private val chunkSizeInPx = (Metric.TILE_SIZE * Metric.MAP_CHUNK_SIZE).toInt()
    private var firstInit = true
    private var lastCameraChunkPosition = Pair(0, 0)
    private var lastCameraZoom = 0f

    fun render() {
        if (firstInit) {
            firstInit = false
            updateCameraChunk()
            prepareChunkForRendering()
        }
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
        updateCameraChunk()
    }

    private fun prepareChunkForRendering() {
        val visible = PosUtil.getVisibleArea(camera)

        for (i in visible.x1..visible.y1) {
            for (j in visible.x2..visible.y2) {
                if (visibleChunks.containsKey(BaseMap.toChunkId(i, j)).not()) {
                    if (map.isChunkDirty(i, j)) {
                        map.loadChunk(i, j)
                    }
                    map.getChunk(i, j)?.let {
                        visibleChunks[BaseMap.toChunkId(i, j)] = it as RenderableChunk
                    }
                }
            }
        }
        removeUnnecessaryChunks(visible)
    }

    private fun removeUnnecessaryChunks(visible: ChunkArea) {
        visibleChunks = visibleChunks.filter { (_, chunk) ->
            visible.contains(chunk.x, chunk.y)
        }.toMutableMap()
    }

    private fun updateCameraChunk() {
        val worldPos = PosUtil.getWorldPosition(camera.position.x, camera.position.y)
        val chunkPos = PosUtil.getChunkPosition(worldPos.first, worldPos.second)
        if (chunkPos != lastCameraChunkPosition) {
            lastCameraChunkPosition = chunkPos
            prepareChunkForRendering()
        }
        val zoom = when (camera) {
            is OrthographicCamera -> camera.zoom
            is PerspectiveCamera -> normalizeZ(camera.position.z)
            else -> 1f
        }
        if (zoom != lastCameraZoom) {
            lastCameraZoom = zoom
            prepareChunkForRendering()
        }
    }

    private fun checkDirty() {
        visibleChunks.forEach { (_, chunk) ->
            if (chunk.isDirty) {
                Logger.info("Chunk ${chunk.x} ${chunk.y} is dirty, reloading it")
                map.loadChunk(chunk.x, chunk.y)
            }
        }
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }
}