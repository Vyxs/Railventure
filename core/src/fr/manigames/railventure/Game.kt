package fr.manigames.railventure

import com.badlogic.gdx.Game
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.client.map.ChunkLoader
import fr.manigames.railventure.client.screen.LoadingScreen
import fr.manigames.railventure.test.TestMap

class Game : Game() {

    companion object {
        const val DEBUG = true
        const val USE_PLAYER_CAMERA = false

        val GAME_WIDTH = Metric.GAME_WIDTH
        val GAME_HEIGHT = Metric.GAME_HEIGHT
    }

    private val chunkLoader: ChunkLoader = ChunkLoader(Assets.instance)

    val map: TestMap = TestMap(chunkLoader::loadChunk)

    fun changeScreen(screen: Screen) {
        this.screen?.dispose()
        screen.init(this)
        setScreen(screen)
    }

    override fun create() {
        changeScreen(LoadingScreen())
    }

    override fun dispose() {
        Assets.instance.dispose()
        Render.dispose()
    }
}