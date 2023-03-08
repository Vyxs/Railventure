package fr.manigames.railventure.common.system

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.ScreenUtils
import fr.manigames.railventure.common.renderer.TileRenderer
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.common.renderer.HudRenderer
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.*

class RenderSystem(
    world: World,
    private val asset: Assets,
    private val camera: Camera
) : System(world) {

    private lateinit var tileRenderer: TileRenderer
    private lateinit var hudRenderer: HudRenderer

    override fun init() {
        tileRenderer = TileRenderer(asset)
        hudRenderer = HudRenderer()
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        world.getEntitiesWithComponent(ComponentType.TILE_RENDERABLE).forEach { entity ->
            val renderComponent: TileRenderComponent = world.getComponent(entity, ComponentType.TILE_RENDERABLE)
            tileRenderer.setProjectionMatrix(camera.combined)
            tileRenderer.renderTile(renderComponent.type, renderComponent.x, renderComponent.y)
        }

        world.getEntitiesWithComponents(ComponentType.HUD_POSITION, ComponentType.TEXT).forEach { entity ->
            val position: HudPositionComponent = world.getComponent(entity, ComponentType.HUD_POSITION)
            val text: TextComponent = world.getComponent(entity, ComponentType.TEXT)
            hudRenderer.setProjectionMatrix(camera.combined)
            hudRenderer.renderText(text.text, position.x, position.y, text.color)
        }

        world.getEntitiesWithComponents(ComponentType.TEXTURE, ComponentType.WORLD_POSITION).forEach { entity ->
            val position: WorldPositionComponent = world.getComponent(entity, ComponentType.WORLD_POSITION)
            val texture: TextureComponent = world.getComponent(entity, ComponentType.TEXTURE)
            tileRenderer.setProjectionMatrix(camera.combined)
            tileRenderer.renderTexture(texture.texture, position.world_x, position.world_y)
        }
    }

    override fun dispose() {
        tileRenderer.dispose()
        hudRenderer.dispose()
    }
}