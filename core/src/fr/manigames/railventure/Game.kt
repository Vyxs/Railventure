package fr.manigames.railventure

import com.badlogic.gdx.Game
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.api.map.base.ChunkLoader
import fr.manigames.railventure.api.map.generation.ProceduralMap
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.client.screen.LoadingScreen
import fr.manigames.railventure.test.ParallelProceduralMap

class Game : Game() {

    companion object {
        const val DEBUG = true
        const val USE_PLAYER_CAMERA = true

        val GAME_WIDTH = Metric.GAME_WIDTH
        val GAME_HEIGHT = Metric.GAME_HEIGHT
    }

    private val chunkLoader: ChunkLoader = fr.manigames.railventure.client.map.ChunkLoader(Assets.instance)

    val map: ProceduralMap = ParallelProceduralMap(chunkLoader::loadChunk)

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