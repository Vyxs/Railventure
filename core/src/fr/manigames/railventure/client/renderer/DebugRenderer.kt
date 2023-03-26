package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.core.Metric
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

    private val shapeRenderer = ShapeRenderer()
    private val spriteBatch = SpriteBatch()
    private val font = BitmapFont()
    val inputProcessor = DebugInputProcessor()

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) = Unit

    fun render() {
        if (inputProcessor.showDebug.not())
            return
        if (inputProcessor.showTileGrid)
            renderGrid()
        if (inputProcessor.showChunkGrid)
            renderChunksGrid()
        if (camera is OrthographicCamera)
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
        shapeRenderer.projectionMatrix = camera.combined
        val tileSize = Metric.TILE_SIZE
        val offset = tileSize
        val startX = camera.position.x - camera.viewportWidth / 2 - offset
        val startY = camera.position.y - camera.viewportHeight / 2 - offset
        val endX = camera.position.x + camera.viewportWidth / 2 + offset
        val endY = camera.position.y + camera.viewportHeight + offset
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
        val chunkSize = Metric.MAP_CHUNK_SIZE
        val tileSize = Metric.TILE_SIZE
        val size = chunkSize * tileSize
        shapeRenderer.projectionMatrix = camera.combined
        val offset = size
        val startX = camera.position.x - camera.viewportWidth / 2 - offset
        val startY = camera.position.y - camera.viewportHeight / 2 - offset
        val endX = camera.position.x + camera.viewportWidth / 2 + offset
        val endY = camera.position.y + camera.viewportHeight / 2 + offset
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
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        val stringsToRender = mutableListOf<String>()
        val camWorldPos = PosUtil.getWorldPosition(camera.position.x, camera.position.y)
        val camChunkPos = PosUtil.getChunkPosition(camera.position.x / Metric.TILE_SIZE, camera.position.y / Metric.TILE_SIZE)

        stringsToRender.add("Camera position: ${camera.position.toRoundedString()}")
        stringsToRender.add("Camera world position: ${(camWorldPos.first).toRoundedString()}, ${(camWorldPos.second).toRoundedString()}")
        stringsToRender.add("Camera chunk position: ${camChunkPos.first}, ${camChunkPos.second}")
        stringsToRender.add("Camera direction: ${camera.direction.toRoundedString()}")
        stringsToRender.add("Camera viewport: ${camera.viewportWidth.toRoundedString()}, ${camera.viewportHeight.toRoundedString()}")

        if (camera is OrthographicCamera) {
            val chunkVisibleHorizontalCount = PosUtil.getChunkVisibleHorizontal(camera.position.x, camera.viewportWidth, camera.zoom)
            val chunkVisibleVerticalCount = PosUtil.getChunkVisibleVertical(camera.position.y, camera.viewportHeight, camera.zoom)
            val chunkVisibleCount = PosUtil.getChunkVisible(camera.position.x.toInt(), camera.position.y.toInt(), camera.viewportWidth, camera.viewportHeight, camera.zoom)

            stringsToRender.add("Camera zoom: ${camera.zoom.toRoundedString()}")
            stringsToRender.add("Camera chunk visible horizontal: $chunkVisibleHorizontalCount")
            stringsToRender.add("Camera chunk visible vertical: $chunkVisibleVerticalCount")
            stringsToRender.add("Camera chunk visible: $chunkVisibleCount")
        }

        world?.let {
            world.getEntitiesWithComponents(ComponentType.PLAYER).forEach { entry ->
                if ((entry.value.first { it.componentType == ComponentType.PLAYER } as PlayerComponent).isHost) {
                    val worldPosition = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent
                    val moveable = entry.value.first { it.componentType == ComponentType.MOVEABLE } as MoveableComponent
                    val chunkPosition = PosUtil.getChunkPosition(worldPosition.world_x, worldPosition.world_y)

                    stringsToRender.add("Player position: ${worldPosition.world_x.toRoundedString()}, ${worldPosition.world_y.toRoundedString()}")
                    stringsToRender.add("Player chunk position: ${chunkPosition.first}, ${chunkPosition.second}")
                    stringsToRender.add("Player speed: ${moveable.speed.toRoundedString()}")
                    stringsToRender.add("Player velocity: ${moveable.velocity.toRoundedString()}")
                    stringsToRender.add("Player acceleration: ${moveable.acceleration.toRoundedString()}")
                }
            }
        }

        stringsToRender.add("FPS: ${Gdx.graphics.framesPerSecond}")
        stringsToRender.add("Delta: ${Gdx.graphics.deltaTime.toRoundedString()}")
        stringsToRender.add("Memory: ${(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024}MB")
        stringsToRender.add("Entities: ${world?.getEntities()?.size ?: 0}")

        shapeRenderer.projectionMatrix = Matrix4().setToOrtho2D(0f, 0f, screenWidth, screenHeight)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0f, 0f, 0f, 0.5f)
        shapeRenderer.rect(0f, 0f, 250f, stringsToRender.size * 20f + 10f)
        shapeRenderer.end()

        spriteBatch.begin()

        font.color = Color.WHITE
        stringsToRender.forEachIndexed { index, string ->
            font.draw(spriteBatch, string, 10f, 20f + index * 20f)
        }
        spriteBatch.end()
    }

    override fun dispose() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
        font.dispose()
    }
}

class DebugInputProcessor : InputProcessor {

    var showDebug = true
        private set
    var showTileGrid = true
        private set

    var showChunkGrid = true
        private set
    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Keys.F1 -> showDebug = !showDebug
            Keys.F2 -> showTileGrid = !showTileGrid
            Keys.F3 -> showChunkGrid = !showChunkGrid
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

}