package com.example.restaurantmanager.ui.showfood.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantmanager.databinding.ItemShowFoodBinding
import com.example.restaurantmanager.model.domain.Food

class ShowFoodAdapter : ListAdapter<Food, ShowFoodAdapter.ShowFoodViewHolder>(itemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowFoodViewHolder {
        return ShowFoodViewHolder.build(parent, viewType)
    }

    override fun onBindViewHolder(holder: ShowFoodViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class ShowFoodViewHolder(private val biding: ItemShowFoodBinding) :
        RecyclerView.ViewHolder(biding.root) {
        fun bindData(item: Food?) {
            biding.food = item

        }

        companion object {
            fun build(parent: ViewGroup, viewType: Int): ShowFoodViewHolder {
                val binding =
                    ItemShowFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ShowFoodViewHolder(binding)
            }
        }
    }
}

private val itemCallBack = object : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem.getId() == newItem.getId()

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem == newItem
}