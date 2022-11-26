package com.example.shoppinglist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {
   suspend fun getShopItem(value: Int) : ShopItem{
        return shopListRepository.getShopItem(value)
    }
}