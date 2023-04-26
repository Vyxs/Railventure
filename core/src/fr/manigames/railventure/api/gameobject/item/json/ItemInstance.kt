package fr.manigames.railventure.api.gameobject.item.json

import fr.manigames.railventure.api.gameobject.item.Item
import fr.manigames.railventure.api.serialize.json.Json

class InvalidItemModelException(message: String) : Exception(message)

class ItemInstance(itemData: ItemData) : Item() {

    companion object {

        fun fromJsonModel(json: String) : ItemInstance {
            return try {
                val itemData = Json().fromJson(json, ItemData::class.java)
                ItemInstance(itemData)
            } catch (e: Exception) {
                throw InvalidItemModelException("Invalid item model: ${e.message}")
            }
        }
    }

    override val key: String = itemData.key
    override val name: String = itemData.name
    override val texture: String = itemData.texture
    override val maxStackSize: Int = itemData.maxStackSize
    override val isStackable: Boolean = itemData.isStackable
    override val isUsable: Boolean = itemData.isUsable
    override val isPlaceable: Boolean = itemData.isPlaceable
    override val isDroppable: Boolean = itemData.isDroppable
    override val isPickable: Boolean = itemData.isPickable
    override val isEquippable: Boolean = itemData.isEquippable
    override val isConsumable: Boolean = itemData.isConsumable
    override val isCraftable: Boolean = itemData.isCraftable
    override val isRepairable: Boolean = itemData.isRepairable
    override val isEnchantable: Boolean = itemData.isEnchantable
    override val isEnchanted: Boolean = itemData.isEnchanted
    override val isDamaged: Boolean = itemData.isDamaged
    override val isDamagable: Boolean = itemData.isDamagable
}