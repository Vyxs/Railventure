package fr.manigames.railventure

import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import com.badlogic.gdx.Game
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.api.map.biome.BiomeType
import fr.manigames.railventure.api.map.biome.json.BiomeData
import fr.manigames.railventure.api.map.biome.json.BiomeInstance
import fr.manigames.railventure.api.serialize.json.Json
import fr.manigames.railventure.client.screen.LoadingScreen

class Game : Game() {

    companion object {
        const val DEFAULT_ENTITY_CAPACITY: Int = 10000
        const val DEBUG = true
        const val USE_PLAYER_CAMERA = true
        const val USE_ORTHOGRAPHIC_CAMERA = true
        const val USE_FREE_CAMERA = false

        val GAME_WIDTH = Metric.GAME_WIDTH
        val GAME_HEIGHT = Metric.GAME_HEIGHT
    }

    fun changeScreen(screen: Screen) {
        this.screen?.dispose()
        screen.init(this)
        setScreen(screen)
    }

    override fun create() {
        val ocean = BiomeInstance(BiomeData("ocean", "Ocean", 10, 100, 1000, 0x0000FF, BiomeType.AQUATIC))

        println(Json().toJson(ocean))
        changeScreen(LoadingScreen())
    }

    override fun dispose() {
        Assets.instance.dispose()
        Render.dispose()
    }
}