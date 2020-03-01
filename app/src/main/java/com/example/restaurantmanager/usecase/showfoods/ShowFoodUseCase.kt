package com.example.restaurantmanager.usecase.showfoods

import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ShowFoodUseCase {
    var foodRepository: FoodRepository
    fun getListFoods(): Flow<MutableList<Food?>?>
    fun getFood(id: Objects): Flow<Food?>
    suspend fun delete(nameFood: String): String
}