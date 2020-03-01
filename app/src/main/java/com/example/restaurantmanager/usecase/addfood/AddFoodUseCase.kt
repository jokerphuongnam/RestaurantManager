package com.example.restaurantmanager.usecase.addfood

import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

interface AddFoodUseCase {
    var foodRepository: FoodRepository
    fun addFood(food: Food?): Flow<Boolean>
}