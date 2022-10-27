package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.ShopItem
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({o1,o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = -1

            init {
                for(i in 1 .. 10)
                    addShopItem(ShopItem("Покупка $i ",i, Random.nextBoolean(), autoIncrementId))
            }

    override fun addShopItem(item: ShopItem) {
        if(autoIncrementId == ShopItem.UNDEFINED_VAL){
            item.id = autoIncrementId++
        }
        shopList.add(item)
        autoIncrementId++
        updateList()
    }

    override fun editShopItem(item: ShopItem) {

        val oldShopitem = getShopItem(item.id)
        shopList.remove(oldShopitem)
        addShopItem(item)

    }


    override fun getShopItem(value: Int): ShopItem {
        return shopList.find {
            it.id == value } ?:
            throw RuntimeException("didn't find that thing with $value")
   }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}