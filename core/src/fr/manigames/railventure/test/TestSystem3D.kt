package fr.manigames.railventure.test

import com.badlogic.gdx.graphics.*
import fr.manigames.railventure.api.ecs.system.System
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.ecs.world.World
import fr.manigames.railventure.generated.R

class ModelInstanceWithBounds(model: Model) : ModelInstance(model) {
    val boundingBox = BoundingBox()
}

class TestSystem3D(
    world: World,
    private val camera: PerspectiveCamera,
) : System(world) {

    private lateinit var modelBatch: ModelBatch
    private lateinit var modelBuilder: ModelBuilder
    private lateinit var dirtTexture: Texture
    private val cubeInstances = mutableListOf<ModelInstanceWithBounds>()
    private val cubeSize = 16f
    private val cubeSpacing = 0f
    private val numCubes = 10
    private lateinit var environment: Environment

    override fun init() {
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, 1f, 0.8f, -0.2f))
        modelBatch = ModelBatch()
        modelBuilder = ModelBuilder()

        dirtTexture = Assets.instance.getTexture(R.Texture.DIRT.path)!!

        for (i in 0 until numCubes) {
            val xPos = i * (cubeSize + cubeSpacing)
            modelBuilder.begin()
            val meshBuilder = modelBuilder.part("box", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong() or VertexAttributes.Usage.TextureCoordinates.toLong(), Material(TextureAttribute.createDiffuse(dirtTexture)))
            addVisibleCubeFaces(meshBuilder, Vector3(xPos, 0f, 0f), cubeSize, camera)
            val cube = modelBuilder.end()
            val cubeInstance = ModelInstanceWithBounds(cube)
            cube.calculateBoundingBox(cubeInstance.boundingBox)
            cubeInstances.add(cubeInstance)
        }

    }

    private fun addVisibleCubeFaces(builder: MeshPartBuilder, position: Vector3, size: Float, camera: PerspectiveCamera) {
        val halfSize = size / 2f
        val corner000 = Vector3(position).sub(halfSize, halfSize, halfSize)
        val corner001 = Vector3(corner000).add(0f, 0f, size)
        val corner010 = Vector3(corner000).add(0f, size, 0f)
        val corner011 = Vector3(corner000).add(0f, size, size)
        val corner100 = Vector3(corner000).add(size, 0f, 0f)
        val corner101 = Vector3(corner000).add(size, 0f, size)
        val corner110 = Vector3(corner000).add(size, size, 0f)
        val corner111 = Vector3(corner000).add(size, size, size)

        val camPos = camera.position

        // Front
        if (camPos.z > position.z) {
            builder.rect(corner010, corner110, corner100, corner000, Vector3.Z)
        }
        // Back
        if (camPos.z < position.z) {
            builder.rect(corner111, corner011, corner001, corner101, Vector3.Z.scl(-1f))
        }
        // Left
        if (camPos.x < position.x) {
            builder.rect(corner000, corner100, corner101, corner001, Vector3.X.scl(-1f))
        }
        // Right
        if (camPos.x > position.x) {
            builder.rect(corner010, corner011, corner111, corner110, Vector3.X)
        }
        // Bottom
        if (camPos.y < position.y) {
            builder.rect(corner100, corner101, corner001, corner000, Vector3.Y.scl(-1f))
        }
        // Top
        if (camPos.y > position.y) {
            builder.rect(corner110, corner111, corner011, corner010, Vector3.Y)
        }
    }

    override fun render(delta: Float) {
        modelBatch.begin(camera)
        for (cubeInstance in cubeInstances) {
            if (camera.frustum.boundsInFrustum(cubeInstance.boundingBox)) {
                modelBatch.render(cubeInstance, environment)
            }
        }
        modelBatch.end()
    }
}