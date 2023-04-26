package fr.manigames.railventure.client.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.video.VideoPlayerCreator
import fr.manigames.railventure.Game
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.screen.Screen

class BootScreen : Screen {

    private val videoPlayer = VideoPlayerCreator.createVideoPlayer()
    private lateinit var changeScreen: (Screen) -> Unit
    private val video : FileHandle = Gdx.files.internal("video/logo_no_sound.webm")
    private val audio : Music = Gdx.audio.newMusic(Gdx.files.internal("sound/logo.mp3"))
    private val batch = Render.spriteBatch
    private var isPlaying = false

    override fun init(game: Game) {
        changeScreen = game::changeScreen
        videoPlayer.setOnCompletionListener { changeScreen(LoadingScreen())}
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        videoPlayer.update()
        if (!isPlaying) {
            isPlaying = true
            videoPlayer.play(video)
            audio.play()
        }

        batch.begin()
        videoPlayer.texture?.let {
            batch.draw(it, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        }
        batch.end()
    }

    override fun dispose() {
        try {
            videoPlayer.dispose()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        audio.dispose()
    }
}