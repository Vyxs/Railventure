package fr.manigames.railventure

import com.badlogic.gdx.Graphics
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import fr.manigames.railventure.api.core.Metric

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        val primary: Graphics.Monitor = Lwjgl3ApplicationConfiguration.getPrimaryMonitor()
        val desktopMode: Graphics.DisplayMode = Lwjgl3ApplicationConfiguration.getDisplayMode(primary)
        val config = Lwjgl3ApplicationConfiguration()

        config.setForegroundFPS(desktopMode.refreshRate)
        config.setWindowedMode(Metric.GAME_WIDTH.toInt(), Metric.GAME_HEIGHT.toInt())
        config.setTitle("Railventure")
        Lwjgl3Application(Game(), config)
    }
}