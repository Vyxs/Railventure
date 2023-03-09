package fr.manigames.railventure.api.world

import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.entity.Entity

class World {

    private val entities: MutableMap<Entity, List<Component>> = mutableMapOf()

    /**
     * Add an entity to the world
     *
     * @param entity The entity to add
     **/
    fun addEntity(entity: Entity) {
        entities[entity] = listOf()
    }

    /**
     * Add an entity to the world
     *
     * @param entity The entity to add
     * @param components The components of the entity
     */
    fun addEntity(entity: Entity, vararg components: Component) {
        entities[entity] = components.toList()
    }

    /**
     * Add an entity to the world
     *
     * @param entity The entity to add
     * @param components The components of the entity
     */
    fun addEntity(entity: Entity, components: List<Component>) {
        entities[entity] = components
    }

    /**
     * Add a component to an entity
     *
     * @param entity The entity to add the component to
     * @param component The component to add
     **/
    fun addComponent(entity: Entity, component: Component) {
        entities[entity] = entities[entity]!! + component
    }

    /**
     * Add components to an entity
     *
     * @param entity The entity to add the components to
     * @param components The components to add
     **/
    fun addComponents(entity: Entity, components: List<Component>) {
        entities[entity] = entities[entity]!! + components
    }

    /**
     * Update an entity in the world
     *
     * @param entity The entity to update
     * @param components The components of the entity
     */
    fun updateEntity(entity: Entity, components: List<Component>) {
        entities[entity] = components
    }

    /**
     * Remove an entity from the world
     *
     * @param entity The entity to remove
     */
    fun removeEntity(entity: Entity) {
        entities.remove(entity)
    }

    /**
     * Remove a component from an entity
     *
     * @param entity The entity to remove the component from
     * @param component The component type to remove
     **/
    fun removeComponent(entity: Entity, component: ComponentType) {
        entities[entity] = entities[entity]!!.filter { it.componentType != component }
    }

    /**
     * Get all entities with specific components
     *
     * @param componentList The component types to search for
     * @return A list of entities with the component
     **/
    fun getEntitiesWithComponents(vararg components: ComponentType): Set<Map.Entry<Entity, List<Component>>> {
        return entities.filter { entry -> entry.value.any { components.contains(it.componentType) } }.entries
    }

    /**
     * Get all entities with specific components
     *
     * @param componentList The component types to search for
     * @return A list of entities with the component
     **/
    fun getEntitiesWithComponents(componentList: List<ComponentType>): Set<Map.Entry<Entity, List<Component>>> {
        return entities.filter { entry -> entry.value.any { componentList.contains(it.componentType) } }.entries
    }

    /**
     * Get all entities
     *
     * @return A list of entities
     **/
    fun getEntities(): List<Entity> {
        return entities.keys.toList()
    }

    /**
     * Get a component of an entity
     *
     * @param entity The entity to get the component from
     * @param componentType The component type to get
     * @return The component
     **/
    @Suppress("UNCHECKED_CAST")
    fun <T : Component> getComponent(entity: Entity, componentType: ComponentType): T {
        return entities[entity]!!.first { it.componentType == componentType } as T
    }

    /**
     * Get all components of an entity
     *
     * @param entity The entity to get the components from
     * @return A list of components
     **/
    fun getComponents(entity: Entity): List<Component> {
        return entities[entity]!!
    }

    /**
     * Check if an entity exists
     *
     * @param entity The entity to check
     * @return True if the entity exists, false otherwise
     **/
    fun hasEntity(entity: Entity): Boolean {
        return entities.containsKey(entity)
    }

    /**
     * Check if an entity has specific components
     *
     * @param entity The entity to check
     * @param componentList The component types to check
     * @return True if the entity has the components, false otherwise
     **/
    fun hasComponents(entity: Entity, vararg components: ComponentType): Boolean {
        return entities[entity]!!.any { components.contains(it.componentType) }
    }

    /**
     * Check if an entity has specific components
     *
     * @param entity The entity to check
     * @param componentList The component types to check
     * @return True if the entity has the components, false otherwise
     **/
    fun hasComponents(entity: Entity, componentList: List<ComponentType>): Boolean {
        return entities[entity]!!.any { componentList.contains(it.componentType) }
    }
}