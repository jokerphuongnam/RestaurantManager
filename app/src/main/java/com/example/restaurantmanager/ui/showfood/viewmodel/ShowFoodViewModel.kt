package com.example.restaurantmanager.ui.showfood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantmanager.model.domain.Food
import com.example.restaurantmanager.usecase.showfoods.ShowFoodUseCase
import com.example.restaurantmanager.usecase.showfoods.ShowFoodUseCaseImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ShowFoodViewModel : ViewModel() {
    private val showFoodUseCase: ShowFoodUseCase by lazy {
        ShowFoodUseCaseImp()
    }

    val listFoodToDisplay: MutableLiveData<MutableList<Food?>?> by lazy {
        MutableLiveData<MutableList<Food?>?>().apply {
            value = null
        }
    }

    val checkDelete: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>(null)
    }

    fun deleteFood(pos: Int) {
        viewModelScope.launch {
            checkDelete.postValue(showFoodUseCase.delete(listFoodToDisplay.value?.get(pos)?.name!!))
        }
    }

    init {
        viewModelScope.launch {
            showFoodUseCase.getListFoods().flowOn(Dispatchers.IO).collect {
                if (it != null)
                    listFoodToDisplay.postValue(it)
                else
                    listFoodToDisplay.postValue(mutableListOf())
            }
        }
    }
}