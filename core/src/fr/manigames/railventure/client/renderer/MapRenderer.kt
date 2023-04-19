package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.CameraUtil
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.common.map.BaseMap

abstract class MapRenderer(
    private val map: RenderableMap,
    protected val camera: Camera
) : Renderer {

    private var firstInit = true
    private var lastCameraChunkPosition = Pair(0, 0)
    private var lastCameraZoom = 0f
    protected var visibleChunks: MutableMap<Long, RenderableChunk> = mutableMapOf()
        private set

    open fun render() {
        updateCameraChunk()
        if (firstInit) {
            firstInit = false
            prepareChunkForRendering()
        } else {
            checkDirty()
        }
    }

    open fun onChunksChange() = Unit

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
        val zoom = when (camera) {
            is OrthographicCamera -> camera.zoom
            is PerspectiveCamera -> CameraUtil.normalizeZ(camera.position.z)
            else -> 1f
        }
        if (chunkPos != lastCameraChunkPosition || zoom != lastCameraZoom) {
            lastCameraChunkPosition = chunkPos
            lastCameraZoom = zoom
            prepareChunkForRendering()
            onChunksChange()
        }
    }

    private fun checkDirty() {
        visibleChunks.forEach { (_, chunk) ->
            if (chunk.isDirty) {
                Logger.info("Chunk ${chunk.x} ${chunk.y} is dirty, reloading it")
                map.loadChunk(chunk.x, chunk.y)
                onChunksChange()
            }
        }
    }
}