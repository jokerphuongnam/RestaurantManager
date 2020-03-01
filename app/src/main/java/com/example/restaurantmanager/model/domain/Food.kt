package com.example.restaurantmanager.model.domain

import kotlin.random.Random

data class Food private constructor(
    private var id: Int?,
    var name: String?,
    var price: Int?,
    var amount: Int?,
    var describle: String?
) {
    constructor(
        name: String?,
        price: Int?,
        amount: Int?,
        describle: String?
    ) : this(null, name, price, amount, describle) {

    }

    constructor(): this ("", 0, 0, "")

    init {
        this.id = Random.nextInt(1000, 9999)
    }

    fun getId(): Int? = id
}