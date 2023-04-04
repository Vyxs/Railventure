package fr.manigames.railventure.api.core

/**
 * Contains all renderers used in the game. They must exist only once to avoid memory leaks or other problems.
 **/
object Render {

    val spriteBatch = com.badlogic.gdx.graphics.g2d.SpriteBatch()

    val shapeRenderer = com.badlogic.gdx.graphics.glutils.ShapeRenderer()

    val bitmapFont = com.badlogic.gdx.graphics.g2d.BitmapFont()

    fun dispose() {
        spriteBatch.dispose()
        shapeRenderer.dispose()
        bitmapFont.dispose()
    }
}