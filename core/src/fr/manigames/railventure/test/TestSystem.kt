package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.StretchViewport
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.R
import fr.manigames.railventure.api.entity.EntityBuilder
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.graphics.font.Color
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.*
import fr.manigames.railventure.common.renderer.DebugRenderer
import java.util.logging.Logger

/**
 * Only for test purpose
 **/
class TestSystem(
    world: World,
    private val assets: Assets,
    private val camera: OrthographicCamera,
    private val viewport: StretchViewport,
    private val logger: Logger
) : System(world) {

    private lateinit var cameraController: CameraController
    private lateinit var debugRenderer: DebugRenderer

    override fun init() {
        debugRenderer = DebugRenderer(camera)
        camera.zoom = 0.25f
        cameraController = CameraController(camera)
        cameraController.init()

        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2, 2))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2, 3))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2, 4))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_BOT_RIGHT), WorldPositionComponent(2, 5))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(3, 5))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(4, 5))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(5, 5))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_BOT_LEFT), WorldPositionComponent(6, 5))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(6, 4))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(6, 3))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(6, 2))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_TOP_LEFT), WorldPositionComponent(6, 1))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(5, 1))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(4, 1))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H), WorldPositionComponent(3, 1))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_TOP_RIGHT), WorldPositionComponent(2, 1))
    }

    override fun render(delta: Float) {
        debugRenderer.render()
    }

    override fun update(delta: Float) {
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