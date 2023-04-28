package fr.manigames.railventure.api.gameobject.item

import fr.manigames.railventure.api.registry.RegistryObject

abstract class Item : RegistryObject {

    abstract override val key: String
    abstract val name: String
    abstract val texture: String
    @Transient open val maxStackSize: Int = 1
    @Transient open val isStackable: Boolean = false
    @Transient open val isUsable: Boolean = false
    @Transient open val isPlaceable: Boolean = false
    @Transient open val isDroppable: Boolean = false
    @Transient open val isPickable: Boolean = false
    @Transient open val isEquippable: Boolean = false
    @Transient open val isConsumable: Boolean = false
    @Transient open val isCraftable: Boolean = false
    @Transient open val isRepairable: Boolean = false
    @Transient open val isEnchantable: Boolean = false
    @Transient open val isEnchanted: Boolean = false
    @Transient open val isDamaged: Boolean = false
    @Transient open val isDamagable: Boolean = false
}