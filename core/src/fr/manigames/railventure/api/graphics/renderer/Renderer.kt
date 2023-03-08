package fr.manigames.railventure.api.graphics.renderer

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.Disposable

interface Renderer : Disposable {

    fun setProjectionMatrix(projectionMatrix: Matrix4?)

}