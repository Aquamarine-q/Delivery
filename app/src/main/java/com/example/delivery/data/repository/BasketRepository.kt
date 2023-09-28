package com.example.delivery.data.repository

import com.example.delivery.domain.model.Product
import javax.inject.Inject

class BasketRepository
@Inject constructor() {

    private var products: MutableList<Product> = mutableListOf()
    private var finalCost = 0

    fun getProducts(): List<Product> {
        return products
    }

    fun getFinalCost():Int{
        return finalCost
    }

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun removeProduct(product: Product) {
        products.remove(product)
    }

    fun addCost(cost: Int) {
        finalCost += cost
    }
}