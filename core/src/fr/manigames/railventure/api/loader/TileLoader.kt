package fr.manigames.railventure.api.loader

import com.badlogic.gdx.Gdx
import fr.manigames.railventure.api.gameobject.tile.json.TileInstance
import fr.manigames.railventure.api.registry.TileRegistry

class TileLoader(
    private val tileRegistry: TileRegistry
) : JsonLoader() {

    companion object {
        private const val TILES_PATH = "model/tile/"
    }

    override fun load() {
        val folder = Gdx.files.internal(TILES_PATH)

        folder.list()
            .filter { it.extension() == "json" }
            .mapNotNull { fileHandle ->
                try {
                    Pair(fileHandle.nameWithoutExtension(), fileHandle.readString())
                } catch (e: Exception) {
                    println("Error while loading file ${fileHandle.nameWithoutExtension()}: ${e.message}")
                    null
                }
            }
            .mapNotNull { (filename, json) ->
                try {
                    TileInstance.fromJsonModel(json)
                } catch (e: Exception) {
                    println("Error while loading tile ${filename}: ${e.message}")
                    null
                }
            }
            .forEach { tile ->
                try {
                    tileRegistry.register(tile)
                } catch (e: Exception) {
                    println("Error while registering tile ${tile.key}: ${e.message}")
                }
            }
    }
}