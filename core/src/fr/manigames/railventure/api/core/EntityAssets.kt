package fr.manigames.railventure.api.core

import com.badlogic.gdx.graphics.Texture

object EntityAssets {

    private val textures = mutableMapOf<String, Texture>()

    fun getTexture(name: String): Texture? {
        return textures[name]
    }

    fun addTexture(name: String, texture: Texture) {
        textures[name] = texture
    }

    fun removeTexture(name: String) {
        textures.remove(name)
    }

    fun clear() {
        textures.clear()
    }
}