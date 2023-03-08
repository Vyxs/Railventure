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
import fr.manigames.railventure.common.component.*

private val assetLoadFunc: (AssetManager) -> Unit = {
    it.load(R.Texture.RAIL_V, Texture::class.java)
    it.load(R.Texture.RAIL_H, Texture::class.java)
    it.load(R.Texture.RAIL_X, Texture::class.java)
    it.load(R.Texture.RAIL_TOP_LEFT, Texture::class.java)
    it.load(R.Texture.RAIL_TOP_RIGHT, Texture::class.java)
    it.load(R.Texture.RAIL_BOT_LEFT, Texture::class.java)
    it.load(R.Texture.RAIL_BOT_RIGHT, Texture::class.java)
    it.load(R.Texture.RAIL_T_BOT, Texture::class.java)
    it.load(R.Texture.RAIL_T_TOP, Texture::class.java)
    it.load(R.Texture.RAIL_T_LEFT, Texture::class.java)
    it.load(R.Texture.RAIL_T_RIGHT, Texture::class.java)
}

class Game : ApplicationListener {

    companion object {
        val GAME_WIDTH = Ratio.R_1280_720.width
        val GAME_HEIGHT = Ratio.R_1280_720.height
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: StretchViewport
    private val assets = Assets()
    private lateinit var world: World
    private val systems: LinkedHashSet<System> = linkedSetOf()

    override fun create() {
        world = World()
        camera = OrthographicCamera()
        viewport = StretchViewport(GAME_WIDTH, GAME_HEIGHT, camera)
        systems.add(
            RenderSystem(world, assets, camera)
        )
        init()

        /////// test
        val entity = EntityBuilder.make()
        world.addEntity(entity, TileRenderComponent(TileType.RAIL_V, 50, 50))

        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2, 2))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2, 3))
        world.addEntity(EntityBuilder.make(), TextureComponent(R.Texture.RAIL_V), WorldPositionComponent(2, 4))

        // hud should not be entity but it's an example, so Hud and Text component should be removed later
        val entity2 = EntityBuilder.make()
        world.addEntity(entity2, TextComponent("Hello ECS World!", Color(0.2f, 0.1f, 0.7f, 1f)), HudPositionComponent(100f, 100f))

        world.addEntity(EntityBuilder.make(), TextComponent("Line of text"), HudPositionComponent(100f, 50f))
    }

    private fun init() {
        systems.forEach(System::init)
        assets.load(assetLoadFunc)
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT)
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
        camera.update()
        systems.forEach { system ->
            system.update(0f)
        }
    }

    override fun dispose() {
        assets.dispose()
        systems.forEach(System::dispose)
    }
}