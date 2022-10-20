package com.example.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(item: Shopitem)
    fun editShopItem(item: Shopitem)
    fun getShopItem(value: Int) : Shopitem
    fun getShopList() : List<Shopitem>
    fun removeShopItem(shopitem: Shopitem)
}