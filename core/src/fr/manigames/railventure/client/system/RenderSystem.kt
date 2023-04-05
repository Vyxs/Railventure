package fr.manigames.railventure.client.system

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.ScreenUtils
import fr.manigames.railventure.client.renderer.TileRenderer
import fr.manigames.railventure.api.ecs.component.ComponentType
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.client.renderer.HudRenderer
import fr.manigames.railventure.api.ecs.world.World
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

        world.getEntitiesWithComponents(ComponentType.TILE_RENDERABLE).forEach { entry ->
            entry.value.first { it.componentType == ComponentType.TILE_RENDERABLE }.let { component ->
                val renderComponent: TileRenderComponent = component as TileRenderComponent
                tileRenderer.setProjectionMatrix(camera.combined)
                tileRenderer.renderTile(renderComponent.type, renderComponent.x, renderComponent.y)
            }
        }

        world.getEntitiesWithComponents(ComponentType.HUD_POSITION, ComponentType.TEXT).forEach { entry ->
            entry.value.first { it.componentType == ComponentType.HUD_POSITION }.let { hud ->
                val position: HudPositionComponent = hud as HudPositionComponent
                entry.value.first { it.componentType == ComponentType.TEXT }.let { component ->
                    val text: TextComponent = component as TextComponent
                    hudRenderer.renderText(text.text, position.x, position.y, text.color)
                }
            }
        }

        world.getEntitiesWithComponents(ComponentType.TEXTURE, ComponentType.WORLD_POSITION).forEach { entry ->
            entry.value.first { it.componentType == ComponentType.WORLD_POSITION }.let { worldPos ->
                val position: WorldPositionComponent = worldPos as WorldPositionComponent
                entry.value.first { it.componentType == ComponentType.TEXTURE }.let { component ->
                    val texture: TextureComponent = component as TextureComponent
                    tileRenderer.setProjectionMatrix(camera.combined)
                    tileRenderer.renderTexture(texture.texture, position.world_x, position.world_y)
                }
            }
        }
    }

    override fun dispose() {
        tileRenderer.dispose()
        hudRenderer.dispose()
    }
}