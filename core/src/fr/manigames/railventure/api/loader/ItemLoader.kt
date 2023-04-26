package fr.manigames.railventure.api.loader

import com.badlogic.gdx.Gdx
import fr.manigames.railventure.api.gameobject.item.json.ItemInstance
import fr.manigames.railventure.api.gameobject.tile.json.TileInstance
import fr.manigames.railventure.api.registry.ItemRegistry

class ItemLoader(
    private val itemRegistry: ItemRegistry
) : JsonLoader() {

    companion object {
        private const val ITEMS_PATH = "model/item/"
    }

    override fun load() {
        val folder = Gdx.files.internal(ITEMS_PATH)

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
                    ItemInstance.fromJsonModel(json)
                } catch (e: Exception) {
                    println("Error while loading item ${filename}: ${e.message}")
                    null
                }
            }
            .forEach { item ->
                try {
                    itemRegistry.register(item)
                } catch (e: Exception) {
                    println("Error while registering item ${item.key}: ${e.message}")
                }
            }
    }
}