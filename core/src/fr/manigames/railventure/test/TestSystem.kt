package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.StretchViewport
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.api.entity.EntityBuilder
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.*
import fr.manigames.railventure.common.composition.PlayerComposition
import fr.manigames.railventure.client.renderer.DebugRenderer
import java.util.logging.Logger

/**
 * Only for test purpose
 **/
class TestSystem(
    world: World,
    private val assets: Assets,
    private val camera: OrthographicCamera,
    private val viewport: StretchViewport,
    private val logger: Logger,
    private val useDebugCamera: Boolean
) : System(world) {

    private lateinit var cameraController: CameraController
    private lateinit var debugRenderer: DebugRenderer
    private lateinit var mapRenderer: MapRenderer
    private lateinit var mapRenderer2: FastMapRenderer
    private lateinit var map: TestMap

    fun getVisibleChunks(cameraX: Int, cameraY: Int, viewportWidth: Int, viewportHeight: Int): Int {
        val tileSize = 16
        val chunkSize = 16
        val viewportInTilesX = viewportWidth / tileSize
        val viewportInTilesY = viewportHeight / tileSize
        val chunkInViewX = (viewportInTilesX / chunkSize) + 1
        val chunkInViewY = (viewportInTilesY / chunkSize) + 1
        val chunkOffsetX = cameraX / (chunkSize * tileSize)
        val chunkOffsetY = cameraY / (chunkSize * tileSize)
        return chunkInViewX * chunkInViewY - (chunkOffsetX * chunkInViewY) - chunkOffsetY
    }

    override fun init() {
        debugRenderer = DebugRenderer(camera, world)
        cameraController = CameraController(camera)
        map = TestMap()
        map.load()
        mapRenderer = MapRenderer(map, assets, camera)
        mapRenderer2 = FastMapRenderer(map, assets, camera)
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
        mapRenderer2.render()
        //mapRenderer.render()
        debugRenderer.render()
    }

    override fun update(delta: Float) {
        if (useDebugCamera)
            cameraController.update(1f)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        debugRenderer.dispose()
    }
}