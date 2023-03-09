package fr.manigames.railventure.api.debug

import java.util.logging.Logger

object Logger {

    private val logger: Logger = Logger.getLogger("RailVenture")

    fun info(message: String) {
        logger.info(message)
    }

    fun warning(message: String) {
        logger.warning(message)
    }

    fun error(message: String) {
        logger.severe(message)
    }

    fun error(message: String, throwable: Throwable) {
        logger.severe(message)
        throwable.printStackTrace()
    }

    fun debug(message: String) {
        logger.fine(message)
    }
}