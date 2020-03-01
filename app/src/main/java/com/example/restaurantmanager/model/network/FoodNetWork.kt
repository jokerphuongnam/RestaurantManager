package com.example.restaurantmanager.model.network

import com.example.restaurantmanager.model.domain.Food
import kotlinx.coroutines.flow.Flow
import java.util.*

interface FoodNetWork {
    fun getListFoods(): Flow<MutableList<Food?>?>
    fun getFood(id: Objects): Flow<Food?>
    fun addFood(food: Food?): Flow<Boolean>
}