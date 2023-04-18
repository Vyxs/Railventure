package fr.manigames.railventure.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.collection.compareEntity
import fr.manigames.railventure.api.type.math.ChunkArea
import fr.manigames.railventure.api.util.PosUtil
import fr.manigames.railventure.client.renderer.EntityRenderer
import fr.manigames.railventure.client.renderer.MapRenderer
import fr.manigames.railventure.common.ecs.component.*

class RenderSystem(
    private val camera: Camera = inject(),
    private val mapRenderer: MapRenderer = inject(),
    private val entityRenderer: EntityRenderer = inject()
) : IteratingSystem(
    family { all(Texture, WorldPosition)},
    comparator = compareEntity { a, b-> b[WorldPosition].world_y.compareTo(a[WorldPosition].world_y) }
) {

    private var visibleChunks: ChunkArea? = null

    override fun onUpdate() {
        visibleChunks = PosUtil.getVisibleArea(camera)
        mapRenderer.setProjectionMatrix(camera.combined)
        mapRenderer.render()
        mapRenderer.update()
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)
        super.onUpdate()
        entityRenderer.flush()
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST)
    }

    override fun onTickEntity(entity: Entity) {
        val texture = entity[Texture]
        val position = entity[WorldPosition]
        val size = entity.getOrNull(WorldSize)

        val visible = visibleChunks ?: return

        if (!visible.containsTile(position.world_x, position.world_y)) {
            return
        }

        entityRenderer.setProjectionMatrix(camera.combined)
        if (size != null) {
            entityRenderer.renderEntity(texture.texture, position.world_x, position.world_y, size)
        } else {
            entityRenderer.renderEntity(texture.texture, position.world_x, position.world_y)
        }
    }
}