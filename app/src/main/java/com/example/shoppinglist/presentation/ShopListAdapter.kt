package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter
    : RecyclerView.Adapter<ShopListAdapter.ShoppingListViewHolder>(){
    private var count = 0
     var listOfShopItem = listOf<ShopItem>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

    class ShoppingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shopItemName = view.findViewById<TextView>(R.id.tv_name)
        val shopItemCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        Log.d("ShopListAdapter","onCreateViewHolder = ${++count}")
        if(viewType == 3748139){
           val view = LayoutInflater.from(parent.context)
               .inflate(R.layout.item_shop_enabled,parent,false)
            return ShoppingListViewHolder(view)
        }
        if(viewType == 91246139){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_disabled,parent,false)
            return ShoppingListViewHolder(view)
        }
        else throw RuntimeException("unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shopItem = listOfShopItem[position]
        holder.shopItemName.text = shopItem.name
        holder.shopItemCount.text = shopItem.count.toString()

    }

    override fun getItemCount(): Int {
        return listOfShopItem.size
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = listOfShopItem[position]
        return if(shopItem.enabled) 3748139 else 91246139
    }

    companion object{
        const val MAX_POOL_SIZE = 20
        const val ENABLED = 3748139
        const val DISABLED = 91246139
    }


}