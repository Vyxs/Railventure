package fr.manigames.railventure.client.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ScreenUtils
import fr.manigames.railventure.Game
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.screen.Screen
import fr.manigames.railventure.api.loader.*
import fr.manigames.railventure.api.registry.*
import fr.manigames.railventure.client.ui.ProgressBar
import fr.manigames.railventure.client.stage.loading.TileEntityAssetTransformer

class LoadingScreen : Screen {

    private val bitmapFont = Render.bitmapFont
    private val shapeRenderer = Render.shapeRenderer
    private val batch = Render.spriteBatch
    private val assets: Assets = Assets.instance

    private lateinit var itemRegistry: ItemRegistry
    private lateinit var tileRegistry: TileRegistry
    private lateinit var tileEntityRegistry: TileEntityRegistry
    private lateinit var biomeRegistry: BiomeRegistry

    private lateinit var tileEntityAssetTransformer: TileEntityAssetTransformer

    private lateinit var changeScreen: (Screen) -> Unit

    private lateinit var assetProgressBar: ProgressBar

    override fun init(game: Game) {
        changeScreen = game::changeScreen

        itemRegistry = game.itemRegistry
        tileRegistry = game.tileRegistry
        tileEntityRegistry = game.tileEntityRegistry
        biomeRegistry = game.biomeRegistry

        assets.load()
        populateRegistries()
        tileEntityAssetTransformer = TileEntityAssetTransformer(tileEntityRegistry.getAll().values)

        val width = Gdx.graphics.width / 5f
        val height = 40f
        val x = Gdx.graphics.width / 2f - width / 2f
        val y = Gdx.graphics.height / 2f - height / 2f
        assetProgressBar = ProgressBar(x, y, width, height).apply { setText("Loading assets...", bitmapFont) }
    }

    override fun render(delta: Float) {
        update()
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        if (tileEntityAssetTransformer.isTransforming()) {
            assetProgressBar.progress = tileEntityAssetTransformer.getTransformProgression()
        } else {
            assetProgressBar.progress = assets.getProgress()
        }
        assetProgressBar.render(font = bitmapFont, shape = shapeRenderer, batch = batch)
    }

    private fun update() {
        assets.update()
        if (tileEntityAssetTransformer.getTransformProgression() == 1f) {
            changeScreen(GameScreen())
        } else if (assets.hasFinishedLoading() && !tileEntityAssetTransformer.isTransforming()) {
            assetProgressBar.setText("Transforming assets...", bitmapFont)
            kotlin.run {
                tileEntityAssetTransformer.transform()
            }
        }
    }

    private fun populateRegistries() {
        registerObjects("Item", ItemLoader(itemRegistry), itemRegistry)
        registerObjects("Tile", TileLoader(tileRegistry), tileRegistry)
        registerObjects("TileEntity", TileEntityLoader(tileEntityRegistry), tileEntityRegistry)
        registerObjects("Biome", BiomeLoader(biomeRegistry), biomeRegistry)

        tileRegistry.finishRegistration()
        tileEntityRegistry.finishRegistration()
    }

    private fun registerObjects(type: String, loader: JsonLoader, registry: Registry<*>) = loader.load().run {
        println("Registering $type...\n${registry.getAll().toSortedMap().values.joinToString("\n") { "$type '${it.key}' registered." }}\nRegistered ${registry.getAll().size} $type.")
    }
}