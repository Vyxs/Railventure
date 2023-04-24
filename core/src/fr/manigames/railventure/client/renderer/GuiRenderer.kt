package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Render

class GuiRenderer {

    private val batch = Render.spriteBatch

    private val texture = Texture(Gdx.files.internal("texture/gui/container/inventory.png"))

    fun render() {
        val x = Gdx.graphics.width / 2f - 400 / 2f
        val y = Gdx.graphics.height / 2f - 400 / 2f

        batch.projectionMatrix = Matrix4().setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        batch.begin()
        batch.draw(texture, x, y, 400f, 400f)
        batch.end()
    }
}