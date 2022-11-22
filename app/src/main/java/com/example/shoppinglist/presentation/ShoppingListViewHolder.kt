package com.example.shoppinglist.presentation

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding

class   ShoppingListViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    val shopItemName = when (binding) {
        is ItemShopDisabledBinding -> binding.tvName
        else -> (binding as ItemShopEnabledBinding).tvName
    }

    val shopItemCount = when (binding) {
        is ItemShopDisabledBinding -> binding.tvCount
        else -> (binding as ItemShopEnabledBinding).tvCount
    }
}