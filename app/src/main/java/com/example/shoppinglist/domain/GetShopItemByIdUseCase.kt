package com.example.shoppinglist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(value: Int) : Shopitem{
        return shopListRepository.getShopItem(value)
    }
}