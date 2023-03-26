package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.utils.viewport.StretchViewport
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.api.entity.EntityBuilder
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.client.input.GameInput
import fr.manigames.railventure.client.map.ChunkLoader
import fr.manigames.railventure.common.component.*
import fr.manigames.railventure.common.composition.PlayerComposition
import fr.manigames.railventure.client.renderer.DebugRenderer
import fr.manigames.railventure.client.renderer.MapRenderer
import java.util.logging.Logger

/**
 * Only for test purpose
 **/
class TestSystem(
    world: World,
    private val assets: Assets,
    private val camera: PerspectiveCamera,
    private val viewport: StretchViewport,
    private val logger: Logger,
    private val useDebugCamera: Boolean,
    private val inputRegistry: GameInput
) : System(world) {

    private lateinit var cameraController: CameraController
    private lateinit var debugRenderer: DebugRenderer
    private lateinit var mapRenderer: MapRenderer
    private lateinit var map: TestMap
    private lateinit var chunkLoader: ChunkLoader

    override fun init() {
        chunkLoader = ChunkLoader(assets)
        debugRenderer = DebugRenderer(camera, world)
        inputRegistry.addInputProcessor(debugRenderer.inputProcessor)
        cameraController = CameraController(camera)
        inputRegistry.addInputProcessor(cameraController)
        map = TestMap(chunkLoader::loadChunk)
        map.generate()
        mapRenderer = MapRenderer(map, camera)
        if (useDebugCamera) {
            cameraController.init()
        }

        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2f, 2f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2f, 3f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2f, 4f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_BOT_RIGHT), WorldPositionComponent(2f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(3f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(4f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(5f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_BOT_LEFT), WorldPositionComponent(6f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(6f, 4f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(6f, 3f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(6f, 2f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_TOP_LEFT), WorldPositionComponent(6f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(5f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(4f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(3f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_TOP_RIGHT), WorldPositionComponent(2f, 1f))

        world.addEntity(EntityBuilder.make(), PlayerComposition(
            worldPosition = WorldPositionComponent(2f, 2f),
            moveable = MoveableComponent(
                maxSpeed = 5f,
                maxAngularSpeed = 1f
            )
        ).toComponents())
    }

    override fun render(delta: Float) {
        mapRenderer.render()
        debugRenderer.render()
    }

    override fun update(delta: Float) {
        if (useDebugCamera)
            cameraController.update(1f)
        mapRenderer.update()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        mapRenderer.dispose()
        debugRenderer.dispose()
    }
}