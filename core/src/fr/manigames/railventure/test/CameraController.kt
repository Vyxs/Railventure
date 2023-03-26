package fr.manigames.railventure.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.MathUtils
import fr.manigames.railventure.api.core.Metric.CAMERA_DEBUG_HEIGHT_ACCELERATION
import fr.manigames.railventure.api.core.Metric.CAMERA_DEBUG_SPEED
import fr.manigames.railventure.api.core.Metric.CAMERA_DEBUG_ZOOM_ACCELERATION
import fr.manigames.railventure.api.core.Metric.CAMERA_HEIGHT_MIN
import fr.manigames.railventure.api.core.Metric.CAMERA_ZOOM_MAX
import fr.manigames.railventure.api.core.Metric.CAMERA_ZOOM_MIN

class CameraController(
    private val camera: Camera
) : InputProcessor {

    fun init() {
        Gdx.input.inputProcessor = this
        if (camera is OrthographicCamera)
            camera.zoom = 0.1f
    }

    fun update(delta: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-delta * CAMERA_DEBUG_SPEED, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(delta * CAMERA_DEBUG_SPEED, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0f, delta * CAMERA_DEBUG_SPEED, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0f, -delta * CAMERA_DEBUG_SPEED, 0f)
        }
        camera.update()
    }

    override fun keyDown(keycode: Int): Boolean = false

    override fun keyUp(keycode: Int): Boolean = false

    override fun keyTyped(character: Char): Boolean = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        if (camera is OrthographicCamera) {
            camera.zoom += amountY * CAMERA_DEBUG_ZOOM_ACCELERATION
            camera.zoom = MathUtils.clamp(camera.zoom, CAMERA_ZOOM_MIN, CAMERA_ZOOM_MAX)
        } else if (camera is PerspectiveCamera) {
            camera.translate(0f, 0f, amountY * CAMERA_DEBUG_HEIGHT_ACCELERATION)
            camera.position.z = MathUtils.clamp(camera.position.z, CAMERA_HEIGHT_MIN, camera.far)
        }
        return false
    }
}