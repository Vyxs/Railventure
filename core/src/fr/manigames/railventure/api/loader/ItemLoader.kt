package fr.manigames.railventure.api.loader

import com.badlogic.gdx.Gdx
import fr.manigames.railventure.api.item.json.ItemInstance
import fr.manigames.railventure.api.registry.ItemRegistry

class ItemLoader(
    private val itemRegistry: ItemRegistry
) : JsonLoader() {

    companion object {
        private const val ITEMS_PATH = "model/item/"
    }

    override fun load() {
        val folder = Gdx.files.internal(ITEMS_PATH)
        folder.list().filter { it.extension() == "json" }.forEach {
            val json = it.readString()
            val item = ItemInstance.fromJsonModel(json)
            try {
                itemRegistry.register(item)
            } catch (e: Exception) {
                println("Error while registering item ${item.key}: ${e.message}")
            }
        }
    }
}