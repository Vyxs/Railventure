package fr.manigames.railventure.api.entity

object EntityBuilder {

    private var id = 0L

    fun make(): Entity = Entity(id++)
}