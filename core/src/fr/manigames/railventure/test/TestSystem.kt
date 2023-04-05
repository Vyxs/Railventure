package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.PerspectiveCamera
import fr.manigames.railventure.api.ecs.entity.EntityBuilder
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.client.input.GameInput
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.common.component.*
import fr.manigames.railventure.common.composition.PlayerComposition
import fr.manigames.railventure.client.renderer.DebugRenderer
import fr.manigames.railventure.client.renderer.MapRenderer
import fr.manigames.railventure.generated.R

/**
 * Only for test purpose
 **/
class TestSystem(
    world: World,
    private val camera: Camera,
    private val useDebugCamera: Boolean,
    private val inputRegistry: GameInput,
    private val map: RenderableMap,
) : System(world) {

    private lateinit var cameraController: CameraController
    private lateinit var debugRenderer: DebugRenderer
    private lateinit var mapRenderer: MapRenderer

    override fun init() {
        debugRenderer = DebugRenderer(camera, world)
        inputRegistry.addInputProcessor(debugRenderer.inputProcessor)
        cameraController = CameraController(camera)
        inputRegistry.addInputProcessor(cameraController)
        mapRenderer = MapRenderer(map, camera)
        if (useDebugCamera) {
            cameraController.init()
        }

        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V.path), WorldPositionComponent(2f, 2f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V.path), WorldPositionComponent(2f, 3f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V.path), WorldPositionComponent(2f, 4f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_BOT_RIGHT.path), WorldPositionComponent(2f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H.path), WorldPositionComponent(3f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H.path), WorldPositionComponent(4f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H.path), WorldPositionComponent(5f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_BOT_LEFT.path), WorldPositionComponent(6f, 5f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V.path), WorldPositionComponent(6f, 4f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V.path), WorldPositionComponent(6f, 3f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V.path), WorldPositionComponent(6f, 2f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_TOP_LEFT.path), WorldPositionComponent(6f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H.path), WorldPositionComponent(5f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H.path), WorldPositionComponent(4f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_H.path), WorldPositionComponent(3f, 1f))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_TOP_RIGHT.path), WorldPositionComponent(2f, 1f))

        world.addEntity(
            EntityBuilder.make(), PlayerComposition(
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

    override fun dispose() {
        mapRenderer.dispose()
        debugRenderer.dispose()
    }
}