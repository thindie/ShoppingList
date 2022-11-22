package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter
    : ListAdapter<ShopItem, ShoppingListViewHolder>
    (ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ShoppingListViewHolder {
        val layout = when(viewType){
            SHOPITEM_ENABLED -> R.layout.item_shop_enabled
            SHOPITEM_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException()
        }

        val binding =
        DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context)
            ,layout
            ,parent
            ,false)
        return ShoppingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {

        val shopItem = getItem(position)
       holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            false
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        holder.shopItemName.text = shopItem.name
        holder.shopItemCount.text = shopItem.count.toString()
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) 3748139 else 91246139
    }

    companion object {
        const val MAX_POOL_SIZE = 20
        const val SHOPITEM_ENABLED = 3748139
        const val SHOPITEM_DISABLED = 91246139
    }

}