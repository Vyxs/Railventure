package fr.manigames.railventure.api.graphics.screen

import com.badlogic.gdx.Screen
import fr.manigames.railventure.Game

interface Screen : Screen {

    fun init(game: Game) = Unit

    override fun render(delta: Float) = Unit

    override fun show() = Unit

    override fun hide() = Unit

    override fun pause() = Unit

    override fun resume() = Unit

    override fun resize(width: Int, height: Int) = Unit

    override fun dispose() = Unit
}