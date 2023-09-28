package com.example.delivery.presentation.viewstate

import com.example.delivery.data.model.Category
import com.example.delivery.domain.model.Product

data class HomeViewState(
    val categories: List<Category> = listOf(
        Category(0, "Роллы"),
        Category(1, "fff"),
        Category(2, "hы")
    ),
    val selectedCategory: Category = Category(0, "Роллы"),
    val products: List<Product> = listOf(),
    val cost: Int = 0,
    val filerState: FilterState = FilterState(
        isWithoutMeat = false, isSpicy = false, isDiscounted = false
    ),
    val isSheetOpen: Boolean = false,
    val isSearchViewOpened: Boolean = false,
    val searchText: String = "",
    val basketProducts: List<Product> = listOf()
)