package com.example.restaurantmanager.repository

import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.model.network.FirebaseImp
import com.example.restaurantmanager.model.network.FoodNetWork
import kotlinx.coroutines.flow.Flow
import java.util.*

class FoodRepositoryImp : FoodRepository {
    private val foodNetWork: FoodNetWork

    init {
        foodNetWork = FirebaseImp()
    }

    override fun getListFoods(): Flow<MutableList<Food?>?> = foodNetWork.getListFoods()
    override fun getFood(id: Objects): Flow<Food?> = foodNetWork.getFood(id)
    override suspend fun addFood(food: Food?) = foodNetWork.addFood(food)
    override suspend fun delete(nameFood: String) = foodNetWork.delete(nameFood)
}