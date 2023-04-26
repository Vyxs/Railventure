package fr.manigames.railventure.api.loader

import com.badlogic.gdx.Gdx
import fr.manigames.railventure.api.gameobject.tile.json.TileInstance
import fr.manigames.railventure.api.gameobject.tileentity.json.TileEntityInstance
import fr.manigames.railventure.api.registry.TileEntityRegistry

class TileEntityLoader(
    private val tileEntityRegistry: TileEntityRegistry,
) : JsonLoader() {

    companion object {
        private const val TILE_ENTITIES_PATH = "model/tileentity/"
    }

    override fun load() {
        val folder = Gdx.files.internal(TILE_ENTITIES_PATH)

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
                    TileEntityInstance.fromJsonModel(json)
                } catch (e: Exception) {
                    println("Error while loading tileEntity ${filename}: ${e.message}")
                    null
                }
            }
            .forEach { tileEntity ->
                try {
                    tileEntityRegistry.register(tileEntity)
                } catch (e: Exception) {
                    println("Error while registering tileEntity ${tileEntity.key}: ${e.message}")
                }
            }
    }
}