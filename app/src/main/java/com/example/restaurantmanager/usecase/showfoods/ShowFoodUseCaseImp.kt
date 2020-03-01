package com.example.restaurantmanager.usecase.showfoods

import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.repository.FoodRepository
import com.example.restaurantmanager.repository.FoodRepositoryImp
import com.example.restaurantmanager.usecase.addfood.AddFoodUseCase
import kotlinx.coroutines.flow.Flow
import java.util.*

class ShowFoodUseCaseImp private constructor(override var foodRepository: FoodRepository) :
    ShowFoodUseCase {
    constructor() : this(FoodRepositoryImp())

    override fun getListFoods(): Flow<MutableList<Food?>?> = foodRepository.getListFoods()

    override fun getFood(id: Objects): Flow<Food?> = foodRepository.getFood(id)
}