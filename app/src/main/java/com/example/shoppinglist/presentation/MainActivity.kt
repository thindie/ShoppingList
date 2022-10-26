package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
          viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            shopListAdapter.listOfShopItem = it
        }
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()
        val recyclerViewShoppingList = findViewById<RecyclerView>(R.id.rv_shop_list)
        recyclerViewShoppingList.adapter = shopListAdapter
        with(recyclerViewShoppingList) {

            recycledViewPool
                .setMaxRecycledViews(ShopListAdapter.SHOPITEM_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool
                .setMaxRecycledViews(ShopListAdapter.SHOPITEM_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
        }
        shopListAdapter.onShopItemLongClickListener = {it -> viewModel.changeEnabledState(it)}
        shopListAdapter.onShopItemClickListener = {it -> Log.d("onShopItemClickListener", "${it.name} status")}
    }
}