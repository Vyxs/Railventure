package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.profiling.GLProfiler
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.api.util.MathUtil.toRoundedString
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.MoveableComponent
import fr.manigames.railventure.common.component.PlayerComponent
import fr.manigames.railventure.common.component.WorldPositionComponent


class DebugRenderer(
    private val camera: Camera,
    private val world: World?
) : Renderer {

    private val resetMatrix = Matrix4().setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val shapeRenderer = Render.shapeRenderer
    private val spriteBatch = Render.spriteBatch
    private val font = Render.bitmapFont
    private val glyphLayout = GlyphLayout(font, "")
    private val glProfiler = GLProfiler(Gdx.graphics)
    val inputProcessor = DebugInputProcessor()

    init {
        inputProcessor.setGpuInfoListener {
            if (it) glProfiler.enable()
            else glProfiler.disable()
        }
        if (inputProcessor.showGpuInfo)
            glProfiler.enable()
    }
    override fun setProjectionMatrix(projectionMatrix: Matrix4?) = Unit

    fun render() {
        if (inputProcessor.showDebug.not())
            return
        if (inputProcessor.showTileGrid)
            renderGrid()
        if (inputProcessor.showChunkGrid)
            renderChunksGrid()
        renderCameraPosition()
        renderDebugInfo()
    }

    private fun renderCameraPosition() {
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.GREEN
        shapeRenderer.circle(camera.position.x, camera.position.y, 5f, 20)
        shapeRenderer.end()
    }

    private fun renderGrid() {
        if (camera !is OrthographicCamera)
            return
        shapeRenderer.projectionMatrix = camera.combined
        val tileSize = Metric.TILE_SIZE
        val offset = tileSize
        val startX = camera.position.x - (camera.viewportWidth * camera.zoom) / 2 - offset
        val startY = camera.position.y - (camera.viewportHeight * camera.zoom) / 2 - offset
        val endX = camera.position.x + (camera.viewportWidth * camera.zoom) / 2 + offset
        val endY = camera.position.y + (camera.viewportHeight * camera.zoom) / 2 + offset
        val startXGrid = (startX / tileSize).toInt()
        val startYGrid = (startY / tileSize).toInt()
        val endXGrid = (endX / tileSize).toInt()
        val endYGrid = (endY / tileSize).toInt()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.RED
        for (x in startXGrid..endXGrid) {
            shapeRenderer.line(x * tileSize, startY, x * tileSize, endY)
        }
        for (y in startYGrid..endYGrid) {
            shapeRenderer.line(startX, y * tileSize, endX, y * tileSize)
        }
        shapeRenderer.end()
    }

    private fun renderChunksGrid() {
        if (camera !is OrthographicCamera)
            return
        val chunkSize = Metric.MAP_CHUNK_SIZE
        val tileSize = Metric.TILE_SIZE
        val size = chunkSize * tileSize
        shapeRenderer.projectionMatrix = camera.combined
        val offset = size
        val startX = camera.position.x - (camera.viewportWidth * camera.zoom) / 2 - offset
        val startY = camera.position.y - (camera.viewportHeight * camera.zoom) / 2 - offset
        val endX = camera.position.x + (camera.viewportWidth * camera.zoom) / 2 + offset
        val endY = camera.position.y + (camera.viewportHeight * camera.zoom) / 2 + offset
        val startXGrid = (startX / size).toInt()
        val startYGrid = (startY / size).toInt()
        val endXGrid = (endX / size).toInt()
        val endYGrid = (endY / size).toInt()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.BLUE
        for (x in startXGrid..endXGrid) {
            shapeRenderer.line(x * size, startY, x * size, endY)
        }
        for (y in startYGrid..endYGrid) {
            shapeRenderer.line(startX, y * size, endX, y * size)
        }
        shapeRenderer.end()
    }

    private fun renderDebugInfo() {
        val info = mutableListOf<String>()
        val offset = 10f

        getCameraInfo(info)
        getPlayerInfo(info)
        getGameInfo(info)
        getGpuInfo(info)

        val strings = info.reversed()

        renderDebugRect(strings, offset)

        spriteBatch.projectionMatrix = resetMatrix
        spriteBatch.begin()
        font.color = Color.WHITE
        strings.forEachIndexed { index, string ->
            font.draw(spriteBatch, string, offset, offset * 2 + index * offset * 2)
        }
        spriteBatch.end()
    }

    private fun getCameraInfo(info: MutableList<String>) {
        val camWorldPos = PosUtil.getWorldPosition(camera.position.x, camera.position.y)
        val camChunkPos = PosUtil.getChunkPosition(camera.position.x / Metric.TILE_SIZE, camera.position.y / Metric.TILE_SIZE)
        info.add("===== Camera =====")
        info.add("Camera position: ${camera.position.toRoundedString()}")
        info.add("Camera world position (y, x): ${(camWorldPos.second).toRoundedString()}, ${(camWorldPos.first).toRoundedString()}")
        info.add("Camera chunk position (y, x): ${camChunkPos.second}, ${camChunkPos.first}")
        info.add("Camera direction: ${camera.direction.toRoundedString()}")
        info.add("Camera viewport: ${camera.viewportWidth.toRoundedString()}, ${camera.viewportHeight.toRoundedString()}")

        if (camera is OrthographicCamera) {
            val chunkVisibleHorizontalCount = PosUtil.getChunkVisibleHorizontal(camera.position.x, camera.viewportWidth, camera.zoom)
            val chunkVisibleVerticalCount = PosUtil.getChunkVisibleVertical(camera.position.y, camera.viewportHeight, camera.zoom)
            val chunkVisibleCount = PosUtil.getChunkVisible(camera.position.x.toInt(), camera.position.y.toInt(), camera.viewportWidth, camera.viewportHeight, camera.zoom)

            info.add("Camera zoom: ${camera.zoom.toRoundedString()}")
            info.add("Camera chunk visible horizontal: $chunkVisibleHorizontalCount")
            info.add("Camera chunk visible vertical: $chunkVisibleVerticalCount")
            info.add("Camera chunk visible: $chunkVisibleCount")
        }
    }

    private fun getPlayerInfo(info: MutableList<String>) {
        world?.let {
            world.getEntitiesWithComponents(ComponentType.PLAYER).forEach { entry ->
                if ((entry.value.first { it.componentType == ComponentType.PLAYER } as PlayerComponent).isHost) {
                    val worldPosition = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent
                    val moveable = entry.value.first { it.componentType == ComponentType.MOVEABLE } as MoveableComponent
                    val chunkPosition = PosUtil.getChunkPosition(worldPosition.world_x, worldPosition.world_y)
                    info.add("===== Player =====")
                    info.add("Player position: ${worldPosition.world_x.toRoundedString()}, ${worldPosition.world_y.toRoundedString()}")
                    info.add("Player chunk position: ${chunkPosition.first}, ${chunkPosition.second}")
                    info.add("Player speed: ${moveable.speed.toRoundedString()}")
                    info.add("Player velocity: ${moveable.velocity.toRoundedString()}")
                    info.add("Player acceleration: ${moveable.acceleration.toRoundedString()}")
                }
            }
        }
    }

    private fun getGameInfo(info: MutableList<String>) {
        info.add("===== Game =====")
        info.add("FPS: ${Gdx.graphics.framesPerSecond}")
        info.add("Delta: ${Gdx.graphics.deltaTime.toRoundedString()}")
        info.add("Memory: ${(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024}MB")
        info.add("Entities: ${world?.getEntities()?.size ?: 0}")
    }

    private fun getGpuInfo(info: MutableList<String>) {
        if (inputProcessor.showGpuInfo) {
            info.add("===== GPU =====")
            info.add("GPU: ${Gdx.graphics.glVersion.rendererString}")
            info.add("GL calls: ${glProfiler.calls}")
            info.add("Draw calls: ${glProfiler.drawCalls}")
            info.add("Shader switches: ${glProfiler.shaderSwitches}")
            info.add("Texture bindings: ${glProfiler.textureBindings}")
            info.add("Vertex count: ${glProfiler.vertexCount.total.toInt()}")
            glProfiler.reset()
        }
    }

    private fun renderDebugRect(strings: List<String>, offset: Float = 10f) {
        var width = 0f
        var height = 0f
        strings.forEach {
            glyphLayout.setText(font, it)
            width = maxOf(width, glyphLayout.width)
            height = maxOf(height, glyphLayout.height)
        }
        shapeRenderer.projectionMatrix = resetMatrix
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0f, 0f, 0f, 0.5f)
        shapeRenderer.rect(0f, 0f, width + offset * 2, strings.size * (height + offset) - offset)
        shapeRenderer.end()
    }

    override fun dispose() {
        glProfiler.disable()
    }
}

class DebugInputProcessor : InputProcessor {

    private var gpuInfoListener: (Boolean) -> Unit = {}
    var showDebug = true
        private set
    var showTileGrid = true
        private set

    var showChunkGrid = true
        private set

    var showGpuInfo = true
        private set(value) {
            field = value
            gpuInfoListener(value)
        }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Keys.F1 -> showDebug = !showDebug
            Keys.F2 -> showTileGrid = !showTileGrid
            Keys.F3 -> showChunkGrid = !showChunkGrid
            Keys.F4 -> showGpuInfo = !showGpuInfo
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean = false

    override fun keyTyped(character: Char): Boolean = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amountX: Float, amountY: Float): Boolean = false
    fun setGpuInfoListener(function: (Boolean) -> Unit) {
        gpuInfoListener = function
    }

}