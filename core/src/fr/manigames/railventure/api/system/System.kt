package fr.manigames.railventure.api.system

import fr.manigames.railventure.api.world.World

/**
 * A system is a part of the game logic.
 * It can be used to update the game state, render the game, or handle user input.
 *
 * @param world The world this system is part of.
 */
abstract class System(
    protected val world: World
) {

    /**
     * Called when the system is created.
     */
    open fun init() = Unit

    /**
     * Called when the Game is shown.
     */
    open fun show() = Unit

    /**
     * Called when the Game is hidden.
     */
    open fun hide() = Unit

    /**
     * Called when the Game should render itself.
     */
    open fun render(delta: Float) = Unit

    /**
     * Update the logic of the system.
     */
    open fun update(delta: Float) = Unit

    /**
     * Called when the Game is resized. This can happen at any point during a non-paused state but will never happen before a call to create().
     */
    open fun resize(width: Int, height: Int) = Unit

    /**
     * Called when the Game is paused, usually when it's not active or visible on-screen. The Game is also paused before it is destroyed.
     */
    open fun pause() = Unit

    /**
     * Called when the Game is resumed from a paused state, usually when it regains focus.
     */
    open fun resume() = Unit

    /**
     * Called when the Game is destroyed. Preceded by a call to pause().
     */
    open fun dispose() = Unit
}