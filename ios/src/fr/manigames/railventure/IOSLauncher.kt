package fr.manigames.railventure

import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication

class IOSLauncher : IOSApplication.Delegate() {
    override fun createApplication(): IOSApplication {
        val config = IOSApplicationConfiguration()
        return IOSApplication(Game(), config)
    }

    companion object {
        @JvmStatic
        fun main(argv: Array<String>) {
            val pool = NSAutoreleasePool()
            val entry: Class<UIApplication>? = null
            UIApplication.main(argv, entry, IOSLauncher::class.java)
            pool.close()
        }
    }
}