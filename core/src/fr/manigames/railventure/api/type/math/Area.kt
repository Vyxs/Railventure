package fr.manigames.railventure.api.type.math

/***
 * An area is two Vector2D that represent the top and bottom of the area.
 *
 * ex:
 *
 *  x1 ───────────── y1
 *  │                │
 *  │                │
 *  x2 ───────────── y2
 *
 * @param T The type of the coordinates
 * @property x1 The x coordinate of the top left corner
 * @property y1 The y coordinate of the top right corner
 * @property x2 The x coordinate of the bottom left corner
 * @property y2 The y coordinate of the bottom right corner
 **/
open class Area<T : Number>(
    open val x1: T,
    open val y1: T,
    open val x2: T,
    open val y2: T
)
