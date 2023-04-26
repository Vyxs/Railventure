package fr.manigames.railventure.api.util

import fr.manigames.railventure.api.core.Metric

object CameraUtil {

    /**
     * Normalize the zoom of the camera to the range [CAMERA_ZOOM_MIN, CAMERA_ZOOM_MAX] from the range [CAMERA_HEIGHT_MIN, CAMERA_HEIGHT_MAX]. Should be used with [PerspectiveCamera].
     *
     * @param z PerspectiveCamera position.z
     * @return the normalized zoom
     */
    fun normalizeZ(z: Float) : Float =
        (z - Metric.CAMERA_HEIGHT_MIN) / (Metric.CAMERA_HEIGHT_MAX - Metric.CAMERA_HEIGHT_MIN) * (Metric.CAMERA_ZOOM_MAX - Metric.CAMERA_ZOOM_MIN) + Metric.CAMERA_ZOOM_MIN
}