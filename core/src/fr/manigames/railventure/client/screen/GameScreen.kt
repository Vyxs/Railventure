package fr.manigames.railventure.client.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.utils.viewport.StretchViewport
import fr.manigames.railventure.Game
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.client.input.GameInput
import fr.manigames.railventure.client.system.PlayerCameraSystem
import fr.manigames.railventure.client.system.PlayerControllerSystem
import fr.manigames.railventure.client.system.RenderSystem
import fr.manigames.railventure.common.system.PhysicSystem
import fr.manigames.railventure.common.system.ProceduralGenerationSystem
import fr.manigames.railventure.test.TestSystem

class GameScreen : Screen {

    private lateinit var camera: Camera
    private lateinit var viewport: StretchViewport
    private val world: World = World()
    private val systems: LinkedHashSet<System> = linkedSetOf()
    private val gameInput: GameInput = GameInput()
    private val assets: Assets = Assets.instance

    override fun init(game: Game) {
        camera = if (Game.USE_ORTHOGRAPHIC_CAMERA) {
            OrthographicCamera()
        } else {
            PerspectiveCamera()
        }
        viewport = StretchViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT, camera)

        systems.addAll(
            listOf(
                RenderSystem(world, assets, camera),
                PhysicSystem(world),
                PlayerControllerSystem(world),
                ProceduralGenerationSystem(world, game.map)
            )
        )
        if (Game.DEBUG) {
            systems.add(TestSystem(world, camera, !Game.USE_PLAYER_CAMERA, gameInput, game.map))
        }
        if (Game.USE_PLAYER_CAMERA) {
            systems.add(PlayerCameraSystem(world, camera))
        }
        if (camera is OrthographicCamera) {
            (camera as OrthographicCamera).setToOrtho(false, viewport.worldWidth, viewport.worldHeight)
        } else if (camera is PerspectiveCamera) {
            camera.position.set(50f, 50f, Metric.CAMERA_HEIGHT)
            camera.lookAt(50f, 50f,0f)
            camera.rotate(30f, 1f, 0f, 0f)
            camera.near = 0f
            camera.far = Metric.CAMERA_HEIGHT_MAX
        }
        systems.forEach(System::init)
        gameInput.bind()
    }

    override fun show() = systems.forEach(System::show)

    override fun render(delta: Float) {
        update()
        systems.forEach { system ->
            system.render(Gdx.graphics.deltaTime)
        }
    }

    private fun update() {
        systems.forEach { system ->
            system.update(Gdx.graphics.deltaTime)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        systems.forEach { system ->
            system.resize(width, height)
        }
    }

    override fun pause() = systems.forEach(System::pause)

    override fun resume() = systems.forEach(System::resume)

    override fun hide() = systems.forEach(System::hide)

    override fun dispose() = systems.forEach(System::dispose)
}