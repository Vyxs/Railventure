package fr.manigames.railventure.client.renderer

import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.font.Color
import fr.manigames.railventure.api.graphics.font.Colors

class HudRenderer {

    private val batch = Render.spriteBatch
    private val font = Render.bitmapFont
    private val defaultColor = Colors.WHITE

    fun renderText(text: String, x: Float, y: Float, color: Color?) {
        val c = color ?: defaultColor

        batch.begin()
        font.setColor(c.r, c.g, c.b, c.a)
        font.draw(batch, text, x, y)
        batch.end()
    }
}