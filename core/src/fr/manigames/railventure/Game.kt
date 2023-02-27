package fr.manigames.railventure

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class Game : ApplicationAdapter() {

    companion object {
        const val GAME_WIDTH = 400f
        const val GAME_HEIGHT = 240f
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var batch: SpriteBatch

    private var tkt: Texture? = null

    override fun create() {
        batch = SpriteBatch()
        camera = OrthographicCamera()
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT)
        tkt = Texture("texture/misc/tkt.png")
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(tkt, GAME_WIDTH / 2 - 16, GAME_HEIGHT / 2 - 16)
        batch.end()
    }

    private fun update() {
        camera.update()
    }

    override fun dispose() {
        batch.dispose()
        tkt!!.dispose()
    }
}