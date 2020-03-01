package com.example.restaurantmanager.ui.addfood.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantmanager.R
import com.example.restaurantmanager.databinding.ActivityAddFoodBinding
import com.example.restaurantmanager.ui.addfood.viewmodel.AddFoodViewModel
import com.example.restaurantmanager.ui.showfood.view.ShowFoodActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AddFoodActivity : AppCompatActivity() {
    companion object {
        const val NAME_FOOD_ADD = "nameFoodAdd"
    }

    private val viewModel: AddFoodViewModel by lazy {
        ViewModelProvider(this).get(AddFoodViewModel::class.java)
    }
    lateinit var binding: ActivityAddFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_food)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food)
        binding.viewmodel = viewModel
        with(binding) {
            addFood.setOnClickListener(addFoodAction)
            cancelAddFood.setOnClickListener(cancelAction)
        }
        with(viewModel) {
            checkSuccessAddFood.observe(this@AddFoodActivity, Observer {
                if (it == true) {
                    setResult(Activity.RESULT_OK, Intent().putExtra(NAME_FOOD_ADD, foodToAdd.name))
                    finish()
                } else
                    Toast.makeText(applicationContext, "Cannot add food", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private val addFoodAction: View.OnClickListener by lazy {
        View.OnClickListener {
            with(viewModel) {
                var empty = true
                binding.apply {
                    if (nameEmpty()) {
                        nameFood.error = "Name food cannot be empty"
                        empty = false
                    }
                    if (priceEmpty()) {
                        priceFood.error = "Price food cannot be empty"
                        empty = false
                    }
                    if (amountEmpty()) {
                        amountFood.error = "Amount food cannot be empty"
                        empty = false
                    }
                    if (describleEmpty()) {
                        describleFood.error = "Describle food cannot be empty"
                        empty = false
                    }
                }
                if (empty) {
                    addFood()
                }
            }
        }
    }
    private val cancelAction: View.OnClickListener by lazy {
        View.OnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onBackPressed() {
        if (!intent.getBooleanExtra(ShowFoodActivity.EMPTY_LIST_DATA, false)) {
            setResult(Activity.RESULT_CANCELED)
            super.onBackPressed()
        } else {
            Toast.makeText(applicationContext, "No food can't come back", Toast.LENGTH_SHORT).show()
        }
    }
}
