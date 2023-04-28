package fr.manigames.railventure.api.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.debug.Logger

class Assets private constructor() {

    companion object {
        val instance = Assets()
        const val TEXTURE_PATH = "texture/"
    }

    private val assetManager = AssetManager()

    /**
     * Load all assets
     *
     * @see [AssetManager.load]
     **/
    fun load() {
        recursiveLoad(TEXTURE_PATH)
    }

    private fun recursiveLoad(path: String) {
        val file = Gdx.files.internal(path)
        if (file.isDirectory) {
            file.list().forEach {
                recursiveLoad(it.path())
            }
        } else if (file.extension() == "png") {
            assetManager.load(path, Texture::class.java)
        }
    }

    /**
     * Update the asset manager
     *
     * @return true if all assets are loaded
     **/
    fun update() : Boolean = assetManager.update()

    /**
     * Finish loading all assets
     *
     * @see [AssetManager.finishLoading]
     **/
    fun finishLoading() = assetManager.finishLoading()

    /**
     * Get the loading progress of the asset manager
     *
     * @return the progress
     **/
    fun getProgress(): Float = assetManager.progress

    /**
     * Dispose the asset manager
     *
     * @see [AssetManager.dispose]
     **/
    fun dispose() = assetManager.dispose()

    /**
     * Get a texture
     *
     * @param name the name of the texture
     * @return the texture
     **/
    fun getTexture(name: String): Texture? {
        return try {
            if (name.isBlank()) return null
            assetManager.get(name, Texture::class.java)
        } catch (e: Exception) {
            Logger.error("Texture $name not found maybe you forgot to load it ?", e)
            null
        }
    }

    /**
     * Check if all assets are loaded
     *
     * @return true if all assets are loaded
     **/
    fun hasFinishedLoading() : Boolean {
        return assetManager.isFinished
    }
}