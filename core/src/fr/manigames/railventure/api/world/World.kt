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
     * Add a component to an entity. If the entity already has a component of the same type, it will not be added.
     * If the entity does not exist, the component will not be added.
     *
     * @param entity The entity to add the component to
     * @param component The component to add
     **/
    fun addComponent(entity: Entity, component: Component) {
        if (entities.containsKey(entity).not())
            return
        if (entities[entity]!!.any { it.componentType == component.componentType })
            return
        entities[entity] = entities[entity]!! + component
    }

    /**
     * Add components to an entity. If the entity already has a component of the same type, it will not be added.
     * If the entity does not exist, the component will not be added.
     *
     * @param entity The entity to add the components to
     * @param components The components to add
     **/
    fun addComponents(entity: Entity, components: List<Component>) {
        if (entities.containsKey(entity).not())
            return
        if (entities[entity]!!.any { it.componentType in components.map { it.componentType } })
            return
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
        if (entities.containsKey(entity).not())
            return
        entities[entity] = entities[entity]!!.filter { it.componentType != component }
    }

    /**
     * Get all entities with specific components. The components at least must be present in the entity.
     *
     * Exemple:
     *
     * Entity 1: Component A, Component B, Component C
     * Entity 2: Component A, Component B
     * Entity 3: Component A, Component C
     *
     * getEntitiesWithComponents(Component A, Component B) will return Entity 1 and Entity 2
     *
     * @param componentList The component types to search for
     * @return A list of entities with the component
     **/
    fun getEntitiesWithComponents(vararg components: ComponentType): Set<Map.Entry<Entity, List<Component>>> {
        return entities.filter { entry -> entry.value.map { it.componentType }.containsAll(components.toList()) }.entries
    }

    /**
     * Get all entities with specific components. The components at least must be present in the entity.
     *
     * Exemple:
     *
     * Entity 1: Component A, Component B, Component C
     * Entity 2: Component A, Component B
     * Entity 3: Component A, Component C
     *
     * getEntitiesWithComponents(Component A, Component B) will return Entity 1 and Entity 2
     *
     * @param componentList The component types to search for
     * @return A list of entities with the component
     **/
    fun getEntitiesWithComponents(componentList: List<ComponentType>): Set<Map.Entry<Entity, List<Component>>> {
        return entities.filter { entry -> entry.value.map { it.componentType }.containsAll(componentList) }.entries
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

    /**
     * Update components of an entity. The components will be added if they don't exist, and updated if they exist.
     **/
    fun updateComponents(entity: Entity, vararg components: Component) {
        components.forEach { component ->
            if (hasComponents(entity, component.componentType)) {
                removeComponent(entity, component.componentType)
            }
            addComponent(entity, component)
        }
    }
}