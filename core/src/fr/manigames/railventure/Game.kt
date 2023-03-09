package fr.manigames.railventure

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.StretchViewport
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.api.graphics.display.Ratio
import fr.manigames.railventure.client.system.RenderSystem
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.client.system.PlayerCameraSystem
import fr.manigames.railventure.client.system.PlayerControllerSystem
import fr.manigames.railventure.common.system.PhysicSystem
import fr.manigames.railventure.test.TestSystem
import java.util.logging.Logger

class Game : ApplicationListener {

    companion object {
        const val DEBUG = true
        const val USE_PLAYER_CAMERA = true

        val GAME_WIDTH = Ratio.R_1280_720.width
        val GAME_HEIGHT = Ratio.R_1280_720.height
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: StretchViewport
    private val assets = Assets()
    private lateinit var world: World
    private val systems: LinkedHashSet<System> = linkedSetOf()
    private val logger: Logger = Logger.getLogger(Game::class.java.name)

    override fun create() {
        world = World()
        camera = OrthographicCamera()
        viewport = StretchViewport(GAME_WIDTH, GAME_HEIGHT, camera)
        systems.addAll(
            listOf(
                RenderSystem(world, assets, camera),
                PhysicSystem(world),
                PlayerControllerSystem(world)
            )
        )
        if (DEBUG) {
            systems.add(TestSystem(world, assets, camera, viewport, logger, !USE_PLAYER_CAMERA))
        }
        if (USE_PLAYER_CAMERA) {
            systems.add(PlayerCameraSystem(world, camera))
        }
        init()
    }

    private fun init() {
        systems.forEach(System::init)
        assets.load(R::assetLoadingFunction)
        camera.setToOrtho(false, viewport.worldWidth, viewport.worldHeight)
        assets.finishLoading()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        systems.forEach { system ->
            system.resize(width, height)
        }
    }

    override fun render() {
        update()
        systems.forEach { system ->
            system.render(Gdx.graphics.deltaTime)
        }
    }

    override fun pause() {
        systems.forEach(System::pause)
    }

    override fun resume() {
        systems.forEach(System::resume)
    }

    private fun update() {
        assets.update()
        systems.forEach { system ->
            system.update(Gdx.graphics.deltaTime)
        }
    }

    override fun dispose() {
        assets.dispose()
        systems.forEach(System::dispose)
    }
}