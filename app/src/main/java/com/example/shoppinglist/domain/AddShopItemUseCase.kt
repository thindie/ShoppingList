package com.example.shoppinglist.domain

class AddShopItemUseCase(private val repository: ShopListRepository) {
    fun addShopItem(item: Shopitem){
        repository.addShopItem(item)
    }
}