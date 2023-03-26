package fr.manigames.railventure.api.core

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType

class Assets {

    private val assetManager = AssetManager()

    /**
     * Load all assets
     * Should be called in [Game.create]
     *
     * @param loadCallback Callback called when all assets are loaded
     *
     * @see [AssetManager.load]
     **/
    fun load(loadCallback: (AssetManager) -> Unit) {
        loadCallback(assetManager)
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
            if (TileType.AIR.assetKey == name) return null
            assetManager.get(name, Texture::class.java)
        } catch (e: Exception) {
            Logger.error("Texture $name not found maybe you forgot to load it ?", e)
            null
        }
    }
}