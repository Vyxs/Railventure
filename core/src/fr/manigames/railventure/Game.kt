package fr.manigames.railventure

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.viewport.StretchViewport
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.api.entity.EntityBuilder
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.graphics.display.Ratio
import fr.manigames.railventure.api.graphics.font.Color
import fr.manigames.railventure.common.system.RenderSystem
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.HudPositionComponent
import fr.manigames.railventure.common.component.TextComponent
import fr.manigames.railventure.common.component.TileRenderComponent
import fr.manigames.railventure.test.CameraController
import fr.manigames.railventure.test.TestSystem
import java.util.logging.Logger

class Game : ApplicationListener {

    companion object {
        const val DEBUG = true
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
                RenderSystem(world, assets, camera)
            )
        )
        if (DEBUG) {
            systems.add(TestSystem(world, assets, camera, viewport, logger))
        }
        init()
    }

    private fun init() {
        systems.forEach(System::init)
        assets.load(R::assetLoadingFunction)
        camera.setToOrtho(false, viewport.worldWidth, viewport.worldHeight)
        assets.finishLoading()

        world.addEntity(EntityBuilder.make(), TileRenderComponent(TileType.RAIL_V, 50, 50))
        world.addEntity(EntityBuilder.make(), TextComponent("Hello ECS World!", Color(0.2f, 0.1f, 0.7f, 1f)), HudPositionComponent(100f, 600f))
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
            system.render(0f)
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
            system.update(1f)
        }
    }

    override fun dispose() {
        assets.dispose()
        systems.forEach(System::dispose)
    }
}