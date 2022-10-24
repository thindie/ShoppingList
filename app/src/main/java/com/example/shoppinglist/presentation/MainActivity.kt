package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var linearLayout_shopList : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        linearLayout_shopList = findViewById(R.id.lin_lay_shop_list)
        viewModel.shopList.observe(this){

            showList(it)
        }

    }
    private fun showList(list: List<ShopItem>){
        linearLayout_shopList.removeAllViews()
        for (item in list){
            val layoutId =
                if(item.enabled)
                R.layout.item_shop_enabled
            else
                R.layout.item_shop_disabled
            val view = LayoutInflater.from(this)
                .inflate(layoutId, linearLayout_shopList, false)
            val shopItemLayoutName = view.findViewById<TextView>(R.id.tv_name)
            val shopItemLayoutCount = view.findViewById<TextView>(R.id.tv_count)
            shopItemLayoutName.text = item.name
            shopItemLayoutCount.text = item.count.toString()

            view.setOnClickListener{
                viewModel.changeEnabledState(item)

            }

            linearLayout_shopList.addView(view)
        }
    }
}