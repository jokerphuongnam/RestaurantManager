package com.example.restaurantmanager.ui.addfood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.usecase.addfood.AddFoodUseCase
import com.example.restaurantmanager.usecase.addfood.AddFoodUseCaseImp
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddFoodViewModel : ViewModel() {
    private val addFoodUseCase: AddFoodUseCase by lazy {
        AddFoodUseCaseImp()
    }
    val checkSuccessAddFood: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>(null)
    }
    val foodToAdd: Food by lazy {
        Food()
    }
    var price: String = ""
    var amount: String = ""
    @InternalCoroutinesApi
    fun addFood() {
        foodToAdd.amount = amount.toInt()
        foodToAdd.price = price.toInt()
        viewModelScope.launch {
            checkSuccessAddFood.postValue(addFoodUseCase.addFood(foodToAdd))
        }
    }

    fun nameEmpty(): Boolean = foodToAdd.name?.isEmpty()!!
    fun priceEmpty() = price.isEmpty()
    fun amountEmpty() = amount.isEmpty()
    fun describleEmpty(): Boolean = foodToAdd.describle?.isEmpty()!!
}