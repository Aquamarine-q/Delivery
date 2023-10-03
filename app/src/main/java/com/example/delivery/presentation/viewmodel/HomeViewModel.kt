package com.example.delivery.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery.data.model.Category
import com.example.delivery.data.repository.BasketRepository
import com.example.delivery.data.repository.ProductRepository
import com.example.delivery.domain.model.Product
import com.example.delivery.presentation.viewstate.FilterState
import com.example.delivery.presentation.viewstate.HomeViewState
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val productRepository: ProductRepository,
    private val basketRepository: BasketRepository,
) : ViewModel() {

    private val _viewState = MutableLiveData(HomeViewState())
    val viewState: LiveData<HomeViewState> = _viewState

    private var products: List<Product> = listOf()

    fun onScreenOpened() {
        viewModelScope.launch {
            products = productRepository.getProducts()
            try {
                _viewState.value = _viewState.value?.copy(
                    categories = productRepository.getCategories(),
                    products = products,
                )
            } catch (exception: IOException) {

            }
        }
    }

    fun onSheetStateChanged(isSheetOpened: Boolean) {
        _viewState.value = _viewState.value?.copy(
            isSheetOpen = isSheetOpened
        )
    }

    fun onCheckedChanged(index: Int, checked: Boolean) {
        when (index) {
            0 -> _viewState.value = _viewState.value?.copy(
                filerState = _viewState.value?.filerState?.copy(isWithoutMeat = checked)
                    ?: getDefaultFilterState(),
                products = if (checked) {
                    products.filter { product -> product.tagIds.contains(2) }
                } else {
                    products
                },
            )

            1 -> _viewState.value = _viewState.value?.copy(
                filerState = _viewState.value?.filerState?.copy(isSpicy = checked)
                    ?: getDefaultFilterState(),
                products = if (checked) {
                    products.filter { product -> product.tagIds.contains(4) }
                } else {
                    products
                },
            )

            2 -> _viewState.value = _viewState.value?.copy(
                filerState = _viewState.value?.filerState?.copy(isDiscounted = checked)
                    ?: getDefaultFilterState(),
                products = if (checked) {
                    products.filter { product -> product.oldPrice != null }
                } else {
                    products
                },
            )
        }
    }

    fun onSearchViewStateChanged(state: Boolean) {
        _viewState.value = _viewState.value?.copy(
            isSearchViewOpened = state
        )
    }

    fun onSearchTextChanged(text: String) {
        _viewState.value = _viewState.value?.copy(
            searchText = text,
            products = products.filter { product ->
                product.name.contains(text) || product.description.contains(text)
            },
        )
    }

    fun onCategoryChanged(category: Category) {
        _viewState.value = _viewState.value?.copy(
            selectedCategory = category,
            products = products.filter { product -> product.categoryId == category.id },
        )
    }

    fun onAddBasketItem(product: Product) {
        basketRepository.addProduct(product)
        _viewState.value = _viewState.value?.copy(
            basketProducts = basketRepository.getProducts(),
            cost = basketRepository.getFinalCost(),
        )
    }

    fun onRemoveBasketItem(product: Product) {
        basketRepository.removeProduct(product)
        _viewState.value = _viewState.value?.copy(
            basketProducts = basketRepository.getProducts(),
            cost = basketRepository.getFinalCost(),
        )
    }

    private fun getDefaultFilterState(): FilterState {
        return FilterState(
            isWithoutMeat = false,
            isSpicy = false,
            isDiscounted = false,
        )
    }
}