package com.example.delivery.presentation.viewstate

import com.example.delivery.domain.model.Product

data class BasketViewState(
    val products: List<Product> = listOf(),
    val cost: Int = 0,
)