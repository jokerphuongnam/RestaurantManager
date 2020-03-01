package com.example.restaurantmanager.usecase.addfood

import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.repository.FoodRepository
import com.example.restaurantmanager.repository.FoodRepositoryImp

class AddFoodUseCaseImp private constructor(override var foodRepository: FoodRepository) :
    AddFoodUseCase {
    constructor() : this(FoodRepositoryImp())

    override fun addFood(food: Food?) = foodRepository.addFood(food)
}