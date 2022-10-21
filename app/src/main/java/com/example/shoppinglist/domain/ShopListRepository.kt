package com.example.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun getShopItem(value: Int) : ShopItem
    fun getShopList() : List<ShopItem>
    fun removeShopItem(shopItem: ShopItem)
}