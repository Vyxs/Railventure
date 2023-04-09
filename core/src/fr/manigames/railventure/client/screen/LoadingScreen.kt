package fr.manigames.railventure.client.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ScreenUtils
import fr.manigames.railventure.Game
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.client.ui.ProgressBar
import fr.manigames.railventure.client.stage.loading.EntityAssetTransformer
import fr.manigames.railventure.generated.R

class LoadingScreen : Screen {

    private val bitmapFont = Render.bitmapFont
    private val shapeRenderer = Render.shapeRenderer
    private val batch = Render.spriteBatch
    private val assets: Assets = Assets.instance
    private val entityAssetTransformer = EntityAssetTransformer()

    private lateinit var changeScreen: (Screen) -> Unit

    private lateinit var assetProgressBar: ProgressBar

    override fun init(game: Game) {
        changeScreen = game::changeScreen

        assets.load(R::loadingConsumer)

        val width = Gdx.graphics.width / 5f
        val height = 40f
        val x = Gdx.graphics.width / 2f - width / 2f
        val y = Gdx.graphics.height / 2f - height / 2f
        assetProgressBar = ProgressBar(x, y, width, height).apply { setText("Loading assets...", bitmapFont) }
    }

    override fun render(delta: Float) {
        update()
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        if (entityAssetTransformer.isTransforming()) {
            assetProgressBar.progress = entityAssetTransformer.getTransformProgression()
        } else {
            assetProgressBar.progress = assets.getProgress()
        }
        assetProgressBar.render(font = bitmapFont, shape = shapeRenderer, batch = batch)
    }

    private fun update() {
        assets.update()
        if (entityAssetTransformer.getTransformProgression() == 1f) {
            changeScreen(GameScreen())
        } else if (assets.hasFinishedLoading() && !entityAssetTransformer.isTransforming()) {
            assetProgressBar.setText("Transforming assets...", bitmapFont)
            kotlin.run {
                entityAssetTransformer.transform()
            }
        }
    }
}