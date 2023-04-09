package fr.manigames.railventure.client.system

import com.badlogic.gdx.graphics.Camera
import fr.manigames.railventure.client.renderer.EntityRenderer
import fr.manigames.railventure.api.ecs.component.ComponentType
import fr.manigames.railventure.api.ecs.system.System
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.client.renderer.HudRenderer
import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.client.map.RenderableMap
import fr.manigames.railventure.client.renderer.MapRenderer
import fr.manigames.railventure.common.ecs.component.*

class RenderSystem(
    world: World,
    private val asset: Assets,
    private val camera: Camera,
    private val map: RenderableMap,
    private val use3D: Boolean
) : System(world) {

    private lateinit var hudRenderer: HudRenderer
    private lateinit var mapRenderer: MapRenderer
    private lateinit var tileRenderer: EntityRenderer

    override fun init() {
        hudRenderer = HudRenderer()
        mapRenderer = MapRenderer(map, camera)
        tileRenderer = EntityRenderer(asset, use3D, camera)
    }

    override fun render(delta: Float) {
        mapRenderer.render()


        world.getEntitiesWithComponents(ComponentType.TEXTURE, ComponentType.WORLD_POSITION).forEach { entry ->
            entry.value.first {it.componentType == ComponentType.TEXTURE}.let { tex ->

                entry.value.first { it.componentType == ComponentType.WORLD_POSITION }.let { component ->
                    val position: WorldPositionComponent = component as WorldPositionComponent
                    val texture: TextureComponent = tex as TextureComponent
                    // if position not in camera, don't render


                    val size = entry.value.firstOrNull { it.componentType == ComponentType.WORLD_SIZE }?.let { component ->
                        val size: WorldSizeComponent = component as WorldSizeComponent
                        size
                    }

                    tileRenderer.setProjectionMatrix(camera.combined)
                    if (size != null) {
                        tileRenderer.renderEntity(texture.texture, position.world_x, position.world_y, size)
                    } else {
                        tileRenderer.renderEntity(texture.texture, position.world_x, position.world_y)
                    }
                }
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
    }

    override fun update(delta: Float) {
        mapRenderer.update()
    }

    override fun dispose() {
        mapRenderer.dispose()
    }
}