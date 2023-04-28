package fr.manigames.railventure.api.gameobject.item.json

data class ItemData(
    val key: String,
    val name: String,
    val texture: String,
    val maxStackSize: Int = 1,
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