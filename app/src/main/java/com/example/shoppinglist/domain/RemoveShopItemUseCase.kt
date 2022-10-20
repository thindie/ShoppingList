package com.example.shoppinglist.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun removeShopItem(shopitem: Shopitem){
        shopListRepository.removeShopItem(shopitem)
    }
}