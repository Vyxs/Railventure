package fr.manigames.railventure.client.ui

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

interface UiElement {

    fun render(batch: SpriteBatch? = null, font: BitmapFont? = null, shape: ShapeRenderer? = null, delta: Float = 0.1f)

    fun update(delta: Float) = Unit

    fun dispose() = Unit
}