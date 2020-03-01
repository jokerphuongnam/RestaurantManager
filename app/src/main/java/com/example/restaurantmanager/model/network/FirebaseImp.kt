package com.example.restaurantmanager.model.network

import com.example.restaurantmanager.model.domain.Food
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.util.*

class FirebaseImp : FoodNetWork {
    private val db = FirebaseFirestore.getInstance()

    @Suppress("NO_REFLECTION_IN_CLASS_PATH")
    @ExperimentalCoroutinesApi
    override fun getListFoods(): Flow<MutableList<Food?>?> = channelFlow {
        Food::class.simpleName?.let {
            db.collection(it).addSnapshotListener { querySnapshot, e ->
                if (e == null) {
                    if (querySnapshot != null) {
                        val list = mutableListOf<Food?>()
                        for (doc in querySnapshot.documents)
                            list.add(doc.toObject(Food::class.java))
                        offer(list)
                        return@addSnapshotListener
                    }
                }
                offer(null)
            }.apply {
                awaitClose {
                    remove()
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun getFood(id: Objects): Flow<Food?> = channelFlow {

    }

    @ExperimentalCoroutinesApi
    override fun addFood(food: Food?): Flow<Boolean> = channelFlow {
        Food::class.simpleName?.let {
            db.collection(it).add(food!!).addOnSuccessListener {
                offer(true)
            }.addOnFailureListener {
                offer(false)
            }
        }
    }
}