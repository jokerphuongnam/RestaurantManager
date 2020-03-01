package com.example.restaurantmanager.repository

import com.example.restaurantmanager.model.domain.Food
import kotlinx.coroutines.flow.Flow
import java.util.*

interface FoodRepository {
    fun getListFoods(): Flow<MutableList<Food?>?>
    fun getFood(id: Objects): Flow<Food?>
    fun addFood(food: Food?) : Flow<Boolean>
}