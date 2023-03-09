package fr.manigames.railventure.common.component

import com.badlogic.gdx.math.Vector2
import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

class MoveableComponent(
    /**
     * The current speed of the entity. Speed is the rate of change of distance over time.
     */
    var speed: Float = 0f,

    /**
     * The current velocity of the entity. Velocity is the rate of change of position over time. It is the speed in a given direction.
     */
    var velocity: Vector2 = Vector2(0f, 0f),

    /**
     * The current acceleration of the entity. Acceleration is the rate of change of velocity over time.
     */
    var acceleration: Float = 0f,

    /**
     * The current orientation of the entity. Orientation is the angle of rotation of the entity.
     */
    var orientation: Float = 0f,

    /**
     * The current angular speed of the entity. Angular speed is the rate of change of angle over time. It is the speed of rotation.
     */
    var angularSpeed: Float = 0f,

    /**
     * The current angular velocity of the entity. Angular velocity is the rate of change of angular speed over time. It is the speed of rotation in a given direction.
     */
    var angularAcceleration: Float = 0f,

    /**
     * Maximum speed of the entity.
     **/
    var maxSpeed: Float = 0f,

    /**
     * Maximum angular speed of the entity.
     **/
    var maxAngularSpeed: Float = 0f
) : Component(ComponentType.MOVEABLE)