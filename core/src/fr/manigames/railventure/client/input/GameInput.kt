package fr.manigames.railventure.client.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor

class GameInput {

    private val inputMultiplexer = InputMultiplexer()

    fun addInputProcessor(inputProcessor: InputProcessor) {
        inputMultiplexer.addProcessor(inputProcessor)
    }

    fun removeInputProcessor(inputProcessor: InputProcessor) {
        inputMultiplexer.removeProcessor(inputProcessor)
    }

    fun bind() {
        Gdx.input.inputProcessor = inputMultiplexer
    }
}