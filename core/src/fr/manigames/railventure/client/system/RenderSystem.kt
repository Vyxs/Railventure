package fr.manigames.railventure.client.system

import com.badlogic.gdx.graphics.Camera
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.collection.compareEntity
import fr.manigames.railventure.client.renderer.EntityRenderer
import fr.manigames.railventure.client.renderer.MapRenderer
import fr.manigames.railventure.common.ecs.component.*

class RenderSystem(
    private val camera: Camera = inject(),
    private val mapRenderer: MapRenderer = inject(),
    private val entityRenderer: EntityRenderer = inject()
) : IteratingSystem(
    family { all(Texture, WorldPosition)},
    comparator = compareEntity { a, b -> a[WorldPosition].world_y.compareTo(b[WorldPosition].world_y) }
) {

    override fun onUpdate() {
        mapRenderer.render()
        mapRenderer.update()
        super.onUpdate()
    }
    override fun onTickEntity(entity: Entity) {
        val texture = entity[Texture]
        val position = entity[WorldPosition]
        val size = entity.getOrNull(WorldSize)

        entityRenderer.setProjectionMatrix(camera.combined)
        if (size != null) {
            entityRenderer.renderEntity(texture.texture, position.world_x, position.world_y, size)
        } else {
            entityRenderer.renderEntity(texture.texture, position.world_x, position.world_y)
        }
    }
}