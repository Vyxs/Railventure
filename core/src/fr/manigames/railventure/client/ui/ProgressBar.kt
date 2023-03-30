package fr.manigames.railventure.client.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

open class ProgressBar(
    protected val x: Float,
    protected val y: Float,
    protected val width: Float,
    protected val height: Float,
    protected val maxProgress: Float = 1f,
    protected val color: Color = Color(0x2596beff),
    protected val background: Color = Color(0x0f697dff),
    protected val textColor: Color = Color.WHITE,
    protected val backgroundGap: Float = 1f,
    protected val innerGap: Boolean = true,
    protected val showPercentage: Boolean = true,
    var progress: Float = 0f
) : UiElement {

    protected var text: GlyphLayout? = null

    fun setText(text: String, font: BitmapFont) {
        this.text = GlyphLayout(font, text)
    }

    override fun render(batch: SpriteBatch?, font: BitmapFont?, shape: ShapeRenderer?, delta: Float) {
        if (shape == null) return
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color = background
        if (innerGap) {
            shape.rect(x + backgroundGap, y + backgroundGap, width - backgroundGap * 2, height - backgroundGap * 2)
        } else {
            shape.rect(x - backgroundGap, y - backgroundGap, width + backgroundGap * 2, height + backgroundGap * 2)
        }
        shape.color = color
        shape.rect(x, y, width * progress / maxProgress, height)
        shape.end()
        if (font == null || batch == null || text == null) return
        batch.begin()
        font.color = textColor
        font.draw(batch, text, x + width / 2f - text!!.width / 2f, y + height / 2f + text!!.height / 2f)

        if (showPercentage) {
            val percentage = (progress / maxProgress * 100).toInt()
            val percentageText = GlyphLayout(font, "$percentage%")
            val offset = 5f
            font.draw(batch, percentageText, x + width  - percentageText.width - offset, y + height / 2f + percentageText.height / 2f)
        }
        batch.end()
    }
}