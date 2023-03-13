package fr.manigames.railventure.ecs_benchmark

import fr.manigames.railventure.api.component.ComponentType
import fr.manigames.railventure.api.entity.Entity
import fr.manigames.railventure.api.entity.EntityBuilder
import fr.manigames.railventure.api.system.System
import fr.manigames.railventure.api.world.World
import fr.manigames.railventure.common.component.TextureComponent
import fr.manigames.railventure.common.component.WorldPositionComponent
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

class SimpleSystem(
    world: World
) : System(world) {

    override fun update(delta: Float) {
        world.getEntitiesWithComponents(ComponentType.WORLD_POSITION).forEach { entry ->
            val pos = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent
            world.updateComponents(entry.key, WorldPositionComponent(pos.world_x + 1, pos.world_y + 1))
        }
    }
}

class ComplexSystem1(
    world: World
) : System(world) {

    private var actionCalls = 0

    override fun update(delta: Float) {
        world.getEntitiesWithComponents(ComponentType.WORLD_POSITION, ComponentType.TEXTURE).forEach { entry ->
            val pos = entry.value.first { it.componentType == ComponentType.WORLD_POSITION } as WorldPositionComponent
            val tex = entry.value.firstOrNull { it.componentType == ComponentType.TEXTURE } as TextureComponent?
            if (actionCalls % 2 == 0) {
                world.updateComponents(entry.key, WorldPositionComponent(pos.world_x + 1, pos.world_y + 1))
                world.addComponent(entry.key, TextureComponent("test2"))
            } else {
                world.removeComponent(entry.key, ComponentType.TEXTURE)
            }
            world.updateComponents(entry.key, TextureComponent((tex?.texture ?: "test")))
            actionCalls++
        }
    }
}

class ComplexSystem2(
    world: World
) : System(world) {

    override fun update(delta: Float) {
        repeat(NUM_ENTITIES) {
            world.removeComponent(Entity(it.toLong()), ComponentType.TEXTURE)
            world.addComponent(Entity(it.toLong()), TextureComponent("test"))
        }
    }
}

@State(Scope.Benchmark)
open class RailventureStateAddRemove {
    lateinit var world: World

    @Setup(value = Level.Iteration)
    fun setup() {
        world = World()
    }
}

@State(Scope.Benchmark)
open class RailventureStateSimple {
    lateinit var world: World
    lateinit var system: SimpleSystem

    @Setup(value = Level.Iteration)
    fun setup() {
        world = World()
        system = SimpleSystem(world)
        repeat(NUM_ENTITIES) {
            world.addEntity(EntityBuilder.make(), WorldPositionComponent(0f, 0f))
        }
    }

    fun update(delta: Float) {
        system.update(1f)
    }
}

@State(Scope.Benchmark)
open class RailventureStateComplex {
    lateinit var world: World
    lateinit var system: ComplexSystem1
    lateinit var system2: ComplexSystem2

    @Setup(value = Level.Iteration)
    fun setup() {
        world = World()
        system = ComplexSystem1(world)
        system2 = ComplexSystem2(world)
        repeat(NUM_ENTITIES) {
            val entity = EntityBuilder.make()
            world.addEntity(entity, WorldPositionComponent(0f, 0f))
            world.addEntity(entity, TextureComponent("test"))
        }
    }

    fun update(delta: Float) {
        system.update(1f)
        system2.update(1f)
    }
}

@Fork(value = WARMUPS)
@Warmup(iterations = WARMUPS)
@Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.SECONDS)
open class RailventureBenchmark {
    @Benchmark
    fun addRemove(state: RailventureStateAddRemove) {
        repeat(NUM_ENTITIES) {
            state.world.addEntity(Entity(it.toLong()))
        }
        repeat(NUM_ENTITIES) {
            state.world.removeEntity(Entity(it.toLong()))
        }
    }

    @Benchmark
    fun simple(state: RailventureStateSimple) {
        repeat(WORLD_UPDATES) {
            state.update(1f)
        }
    }

    @Benchmark
    fun complex(state: RailventureStateComplex) {
        repeat(WORLD_UPDATES) {
            state.update(1f)
        }
    }
}