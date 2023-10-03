package com.example.delivery.presentation.viewstate

import com.example.delivery.data.model.Category
import com.example.delivery.domain.model.Product
import com.example.delivery.domain.model.Tag

data class HomeViewState(
    val categories: List<Category> = listOf(),
    val selectedCategory: Category = Category(0, ""),
    val products: List<Product> = listOf(),
    val cost: Int = 0,
    val tags: List<Tag> = listOf(),
    val isSheetOpen: Boolean = false,
    val isSearchViewOpened: Boolean = false,
    val searchText: String = "",
    val basketProducts: List<Product> = listOf(),
)