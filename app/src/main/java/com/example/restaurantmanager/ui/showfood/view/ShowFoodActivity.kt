package com.example.restaurantmanager.ui.showfood.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.restaurantmanager.R
import com.example.restaurantmanager.databinding.ActivityShowFoodBinding
import com.example.restaurantmanager.ui.addfood.view.AddFoodActivity
import com.example.restaurantmanager.ui.showfood.viewmodel.ShowFoodViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ShowFoodActivity : AppCompatActivity() {
    private fun toAddActivity() = startActivityForResult(addIntent, ADD_REQUEST_CODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_show_food)
        val binding: ActivityShowFoodBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_show_food
        )
        with(binding) {
            with(viewModel) {
                with(foodRecycler) {
                    adapter = this@ShowFoodActivity.showFoodAdapter.apply {
                        listFoodToDisplay.observe(this@ShowFoodActivity, Observer {
                            if (it?.size == 0) {
                                Toast.makeText(
                                    applicationContext,
                                    "List food be empty will to add food",
                                    Toast.LENGTH_SHORT
                                ).show()
                                toAddActivity()
                            } else
                                submitList(it)
                        })
                    }
                    //contructer (2 cột, theo dọc)
//                    layoutManager =
//                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    layoutManager = LinearLayoutManager(applicationContext)
                    itemTouchHelper.attachToRecyclerView(this)
                }
                addFoodBtn.setOnClickListener(toAddFoodActivity)
            }
        }
    }

    companion object {
        const val ADD_REQUEST_CODE = 1901
    }

    private val viewModel: ShowFoodViewModel by lazy {
        ViewModelProvider(this).get(ShowFoodViewModel::class.java)
    }

    private val addIntent: Intent by lazy {
        Intent(applicationContext, AddFoodActivity::class.java)
    }

    private val toAddFoodActivity: View.OnClickListener by lazy {
        View.OnClickListener {
            toAddActivity()
        }
    }
    private val showFoodAdapter: ShowFoodAdapter by lazy {
        ShowFoodAdapter().apply {
            submitList(viewModel.listFoodToDisplay.value)
        }
    }
    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return 0
            }

            //kéo để di chuyển
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                val list = viewModel.listFoodToDisplay.value
                list?.set(from, list[to].also {
                    list[to] = list[from]
                })
                return true
            }

            //vuốt trái để xoá
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction){
                    ItemTouchHelper.LEFT->{
                        viewModel.listFoodToDisplay.value?.removeAt(viewHolder.adapterPosition)
                    }
                }
            }
        })
    }
}