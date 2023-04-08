package fr.manigames.railventure.client.system

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.ScreenUtils
import fr.manigames.railventure.client.renderer.TileRenderer
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
    private val map: RenderableMap
) : System(world) {

    private lateinit var hudRenderer: HudRenderer
    private lateinit var mapRenderer: MapRenderer

    override fun init() {
        hudRenderer = HudRenderer()
        mapRenderer = MapRenderer(map, camera)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        mapRenderer.render()

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
        hudRenderer.dispose()
    }
}