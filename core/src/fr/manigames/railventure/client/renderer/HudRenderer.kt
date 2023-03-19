package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.graphics.font.Color
import fr.manigames.railventure.api.graphics.font.Colors
import fr.manigames.railventure.api.graphics.renderer.Renderer

class HudRenderer : Renderer {

    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private val defaultColor = Colors.WHITE

    fun renderText(text: String, x: Float, y: Float, color: Color?) {
        var color = color ?: defaultColor

        batch.begin()
        font.setColor(color.r, color.g, color.b, color.a)
        font.draw(batch, text, x, y)
        batch.end()
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }

    override fun dispose() {
        batch.dispose()
    }
}