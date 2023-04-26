package fr.manigames.railventure.api.gameobject.item.json

import fr.manigames.railventure.api.gameobject.item.Item

data class ItemData(
    val key: String,
    val name: String,
    val texture: String,
    val maxStackSize: Int = Item.DEFAULT_ITEM_STACK_SIZE,
    val isStackable: Boolean = false,
    val isUsable: Boolean = false,
    val isPlaceable: Boolean = false,
    val isDroppable: Boolean = false,
    val isPickable: Boolean = false,
    val isEquippable: Boolean = false,
    val isConsumable: Boolean = false,
    val isCraftable: Boolean = false,
    val isRepairable: Boolean = false,
    val isEnchantable: Boolean = false,
    val isEnchanted: Boolean = false,
    val isDamaged: Boolean = false,
    val isDamagable: Boolean = false
)