package fr.manigames.railventure.test

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy
import com.badlogic.gdx.graphics.g3d.decals.Decal
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import fr.manigames.railventure.api.core.Assets

class TreeExample : ApplicationAdapter() {
    lateinit var perspectiveCamera: PerspectiveCamera
    lateinit var decalBatch: DecalBatch
    lateinit var treeDecal: Decal
    lateinit var camController: CameraInputController
    lateinit var spriteBatch: SpriteBatch
    lateinit var groundTexture: Texture

    override fun create() {
        Assets.instance.load()
        Assets.instance.finishLoading()

        perspectiveCamera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())


        /*
        perspectiveCamera.position.set(50f, 50f, Metric.CAMERA_HEIGHT)
        perspectiveCamera.lookAt(50f, 50f,0f)
        perspectiveCamera.rotate(30f, 1f, 0f, 0f)
        perspectiveCamera.near = 0f
        perspectiveCamera.far = Metric.CAMERA_HEIGHT_MAX
        * **/
        perspectiveCamera.position.set(0f, 5f, 10f)
        perspectiveCamera.lookAt(0f, 0f, 0f)
        perspectiveCamera.near = 1f
        perspectiveCamera.far = 1000f
        perspectiveCamera.rotate(50f, 1f, 0f, 0f)
        perspectiveCamera.update()

        decalBatch = DecalBatch(CameraGroupStrategy(perspectiveCamera))

        groundTexture = Assets.instance.getTexture("texture/tile/grass.png")!!
        spriteBatch = SpriteBatch()

        val treeTexture = Assets.instance.getTexture("texture/foliage/summer/tree_1.png")
        val treeRegion = TextureRegion(treeTexture)

        treeDecal = Decal.newDecal(treeRegion, true)
        treeDecal.setDimensions(treeTexture!!.width.toFloat() / 16f, treeTexture.height.toFloat() / 16f)
        treeDecal.setPosition(0f, 0f, 0f)
        treeDecal.setRotationY(90f)
        treeDecal.setRotationX(90f)

        camController = CameraInputController(perspectiveCamera)
        Gdx.input.inputProcessor = camController
    }

    override fun render() {
        // Effacez l'écran
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)

        camController.update()
        // Mettez à jour la caméra
        perspectiveCamera.update()
        spriteBatch.projectionMatrix = perspectiveCamera.combined
        spriteBatch.begin()
        spriteBatch.draw(groundTexture, 0f, 0f, 16f, 16f)
        spriteBatch.draw(groundTexture, 16f, 0f, 16f, 16f)
        spriteBatch.draw(groundTexture, 16f, 16f, 16f, 16f)
        spriteBatch.end()

        // Dessinez le Decal de l'arbre
        decalBatch.add(treeDecal)
        decalBatch.flush()

        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST)
    }

    override fun dispose() {
        decalBatch.dispose()
    }
}