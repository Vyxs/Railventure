package fr.manigames.railventure.api.util

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlin.math.cos
import kotlin.math.sin

object MathUtil {

    /**
     * Returns the vector as a string with a precision of 2 decimal places.
     **/
    fun Vector3.toRoundedString(): String {
        return String.format("%.2f, %.2f, %.2f", this.x, this.y, this.z)
    }

    /**
     * Returns the vector as a string with a precision of 2 decimal places.
     **/
    fun Vector2.toRoundedString(): String {
        return String.format("%.2f, %.2f", this.x, this.y)
    }

    /**
     * Returns the float as a string with a precision of 2 decimal places.
     **/
    fun Float.toRoundedString(): String {
        return String.format("%.2f", this)
    }

    /**
     * Converts an angle in degrees to a normalized vector.
     **/
    fun Number.angleToNormalizedVector(): Vector2 {
        return Vector2(cos(this.toDouble().toRadians()).toFloat(), sin(this.toDouble().toRadians()).toFloat()).nor()
    }

    /**
     * Convert degrees to radians.
     **/
    fun Number.toRadians(): Double {
        return this.toDouble() * Math.PI / 180
    }
}