package fr.manigames.railventure.client.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.world
import fr.manigames.railventure.Game
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.api.map.generation.ProceduralMap
import fr.manigames.railventure.client.input.GameInput
import fr.manigames.railventure.client.renderer.*
import fr.manigames.railventure.client.system.PlayerCameraSystem
import fr.manigames.railventure.client.system.PlayerControllerSystem
import fr.manigames.railventure.client.system.RenderSystem
import fr.manigames.railventure.common.ecs.component.Move
import fr.manigames.railventure.common.ecs.component.Player
import fr.manigames.railventure.common.ecs.component.Texture
import fr.manigames.railventure.common.ecs.component.WorldPosition
import fr.manigames.railventure.common.ecs.system.PhysicSystem
import fr.manigames.railventure.common.ecs.system.ProceduralGenerationSystem
import fr.manigames.railventure.test.TestSystem
import java.util.*


class GameScreen : Screen {

    private val gameInput: GameInput = GameInput()
    private val assets: Assets = Assets.instance
    private val map = ProceduralMap()
    private lateinit var world: World
    private lateinit var camera: Camera
    private lateinit var viewport: ExtendViewport
    private lateinit var groundMapRenderer: GroundMapRenderer
    private lateinit var objectMapRenderer: ObjectMapRenderer
    private lateinit var guiRenderer: GuiRenderer
    private lateinit var entityRenderer: EntityRenderer
    private lateinit var debugRenderer: DebugRenderer
    private var mainPlayer: Entity? = null

    override fun init(game: Game) {
        setCamera(Game.USE_ORTHOGRAPHIC_CAMERA)
        groundMapRenderer = GroundMapRenderer(map, camera)
        objectMapRenderer = ObjectMapRenderer(map, camera, !Game.USE_ORTHOGRAPHIC_CAMERA, game.tileEntityRegistry)
        guiRenderer = GuiRenderer()
        entityRenderer = EntityRenderer(assets, !Game.USE_ORTHOGRAPHIC_CAMERA, camera)
        debugRenderer = DebugRenderer(camera, map)

        initEcs(game)

        gameInput.bind()
        mainPlayer = world.entity {
            it += Player("Dev", UUID.randomUUID(), isReady = true, isHost = true)
            it += WorldPosition(2f, 2f)
            it += Move(maxSpeed = 5f, maxAngularSpeed = 1f)
            it += Texture("texture/wagon/wagon.png")
        }
    }

    private fun initEcs(game: Game) {
        world = world(entityCapacity = Game.DEFAULT_ENTITY_CAPACITY) {
            injectables {
                add(game.itemRegistry)
                add(game.tileRegistry)
                add(game.tileEntityRegistry)
                add(game.biomeRegistry)
                add(camera)
                add(groundMapRenderer)
                add(objectMapRenderer)
                add(guiRenderer)
                add(entityRenderer)
                add(map)
                add(debugRenderer)
                add(gameInput)
                add("useDebugCamera", !Game.USE_PLAYER_CAMERA)
                add("useFreeCamera", Game.USE_FREE_CAMERA)
            }

            systems {
                add(RenderSystem())
                add(PhysicSystem())
                add(PlayerControllerSystem())
                add(ProceduralGenerationSystem())

                if (Game.DEBUG) add(TestSystem())
                if (Game.USE_PLAYER_CAMERA) add(PlayerCameraSystem())
            }
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        world.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        world.dispose()
        debugRenderer.dispose()
    }

    private fun setCamera(orthographic: Boolean) {
        if (orthographic) {
            camera = OrthographicCamera()
            viewport = ExtendViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT, camera)
            (camera as OrthographicCamera).setToOrtho(false, viewport.worldWidth, viewport.worldHeight)
        } else {
            camera = PerspectiveCamera()
            camera.position.set(50f, 50f, Metric.CAMERA_HEIGHT)
            camera.lookAt(50f, 50f,0f)
            camera.rotate(30f, 1f, 0f, 0f)
            camera.near = 1f
            camera.far = 3000f
            viewport = ExtendViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT, camera)
        }
    }
}