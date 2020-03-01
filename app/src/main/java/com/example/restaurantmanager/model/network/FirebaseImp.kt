package com.example.restaurantmanager.model.network

import com.example.restaurantmanager.model.domain.Food
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class FirebaseImp : FoodNetWork {
    private val db = FirebaseFirestore.getInstance()

    @Suppress("NO_REFLECTION_IN_CLASS_PATH")
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

    override fun getFood(id: Objects): Flow<Food?> = channelFlow {

    }

    override suspend fun addFood(food: Food?) = suspendCancellableCoroutine<Boolean> { cont ->
        val document = db.collection(Food::class.simpleName!!).document(food?.name!!)
        document.get().addOnSuccessListener {
            if (!it.exists()) {
                document.set(food)
                cont.resume(true)
            } else
                cont.resume(false)
        }
    }

    override suspend fun delete(nameFood: String) = suspendCancellableCoroutine<String> { cont ->
        Food::class.simpleName?.let {
            db.collection(it).document(nameFood).delete().addOnSuccessListener {
                cont.resume(nameFood)
            }.addOnFailureListener {
                cont.resume("")
            }
        }
    }
}