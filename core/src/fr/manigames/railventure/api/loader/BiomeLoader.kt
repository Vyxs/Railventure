package fr.manigames.railventure.api.loader

import com.badlogic.gdx.Gdx
import fr.manigames.railventure.api.gameobject.tile.json.TileInstance
import fr.manigames.railventure.api.map.biome.json.BiomeInstance
import fr.manigames.railventure.api.registry.BiomeRegistry

class BiomeLoader(
    private val biomeRegistry: BiomeRegistry
) : JsonLoader() {

    companion object {
        private const val BIOMES_PATH = "model/biome/"
    }

    override fun load() {
        val folder = Gdx.files.internal(BIOMES_PATH)

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
                    BiomeInstance.fromJsonModel(json)
                } catch (e: Exception) {
                    println("Error while loading biome ${filename}: ${e.message}")
                    null
                }
            }
            .forEach { biome ->
                try {
                    biomeRegistry.register(biome)
                } catch (e: Exception) {
                    println("Error while registering biome ${biome.key}: ${e.message}")
                }
            }
    }
}