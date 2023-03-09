package fr.manigames.railventure.common.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.graphics.renderer.Renderer


class DebugRenderer(
    private val camera: Camera
) : Renderer {

    private val shapeRenderer = ShapeRenderer()
    private val spriteBatch = SpriteBatch()
    private val font = BitmapFont()

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) = Unit

    fun render() {
        renderGrid()
        if (camera is OrthographicCamera)
            renderCameraPosition()
        renderDebugInfo()
    }

    private fun renderDebugInfo() {
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()

        shapeRenderer.projectionMatrix = Matrix4().setToOrtho2D(0f, 0f, screenWidth, screenHeight)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0f, 0f, 0f, 0.5f)
        shapeRenderer.rect(0f, 0f, 250f, 120f)
        shapeRenderer.end()

        spriteBatch.begin()
        font.draw(spriteBatch, "Camera position: ${camera.position}", 10f, 20f)
        font.draw(spriteBatch, "Camera world position: ${camera.position.x / Metric.TILE_SIZE}, ${camera.position.y / Metric.TILE_SIZE}", 10f, 40f)
        font.draw(spriteBatch, "Camera direction: ${camera.direction}", 10f, 60f)
        font.draw(spriteBatch, "Camera viewport: ${camera.viewportWidth}, ${camera.viewportHeight}", 10f, 80f)

        if (camera is OrthographicCamera) {
            font.draw(spriteBatch, "Camera zoom: ${camera.zoom}", 10f, 100f)
        }
        spriteBatch.end()
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

    override fun dispose() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
        font.dispose()
    }
}