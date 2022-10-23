package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun getShopItem(value: Int) : ShopItem
    fun getShopList() : LiveData<List<ShopItem>>
    fun removeShopItem(shopItem: ShopItem)
}