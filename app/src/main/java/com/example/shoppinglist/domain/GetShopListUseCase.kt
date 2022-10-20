package com.example.shoppinglist.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository){
    fun getShopList() : List<Shopitem>{
        return shopListRepository.getShopList()
    }
}