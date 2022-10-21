package com.example.shoppinglist.domain

data class ShopItem(

    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id : Int = UNDEFINED_VAL,
)
    {
    companion object{
        const val UNDEFINED_VAL = -1
        }
    }


