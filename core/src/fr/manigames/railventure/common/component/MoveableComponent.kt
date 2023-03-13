package fr.manigames.railventure.common.component

import com.badlogic.gdx.math.Vector2
import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

class MoveableComponent(
    /**
     * The current speed of the entity. Speed is the rate of change of distance over time.
     */
    val speed: Float = 0f,

    /**
     * The current velocity of the entity. Velocity is the rate of change of position over time. It is the speed in a given direction.
     */
    val velocity: Vector2 = Vector2(0f, 0f),

    /**
     * The current acceleration of the entity. Acceleration is the rate of change of velocity over time.
     */
    val acceleration: Float = 0f,

    /**
     * The current orientation of the entity. Orientation is the angle of rotation of the entity.
     */
    val orientation: Float = 0f,

    /**
     * The current angular speed of the entity. Angular speed is the rate of change of angle over time. It is the speed of rotation.
     */
    val angularSpeed: Float = 0f,

    /**
     * The current angular velocity of the entity. Angular velocity is the rate of change of angular speed over time. It is the speed of rotation in a given direction.
     */
    val angularAcceleration: Float = 0f,

    /**
     * Maximum speed of the entity.
     **/
    val maxSpeed: Float = 0f,

    /**
     * Maximum angular speed of the entity.
     **/
    val maxAngularSpeed: Float = 0f
) : Component(ComponentType.MOVEABLE)