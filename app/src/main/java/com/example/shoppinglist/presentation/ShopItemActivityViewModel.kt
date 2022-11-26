package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemByIdUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShopItemActivityViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()
    private val scope = CoroutineScope(Dispatchers.IO)


    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun addShopItem(name: String?, count: String?) {
        scope.launch {
            val nameToAdd = parseName(name)
            val countToAdd = parseCount(count)
            val isDataOk = validateInput(nameToAdd, countToAdd)
            if (isDataOk) {
                val shopItemToAdd =
                    ShopItem(nameToAdd, countToAdd, true)
                addShopItemUseCase.addShopItem(shopItemToAdd)
                finishScreen()
            }
        }

    }

    fun getShopItem(shopItemID: Int) {
        scope.launch {
            val item = getShopItemByIdUseCase
                .getShopItem(shopItemID)
            _shopItem.postValue(item)
        }

    }

    fun editShopItem(name: String?, count: String?) {
        scope.launch {
            val nameToAdd = parseName(name)
            val countToAdd = parseCount(count)
            val isDataOk = validateInput(nameToAdd, countToAdd)
            if (isDataOk) {
                _shopItem.value?.let {
                    val shopItemToAdd = it
                        .copy(name = nameToAdd, count = countToAdd)
                    editShopItemUseCase.editShopItem(shopItemToAdd)
                    finishScreen()
                }
            }
        }

    }

    public fun resetErrorInputName(toChange: Boolean) {
        _errorInputName.postValue(false)
    }

    public fun resetErrorInputCount(toChange: Boolean) {
        _errorInputCount.postValue(false)
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        val parsedString = parseName(count)
        return try {
            parsedString.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        val result = true
        if (name.isBlank()) {
            _errorInputName.postValue(true)
            return false
        }
        if (count <= 0) {
            _errorInputCount.postValue(true)
            return false
        }
        return result
    }

    private fun finishScreen() {
        _shouldCloseScreen.postValue(Unit)
    }

}