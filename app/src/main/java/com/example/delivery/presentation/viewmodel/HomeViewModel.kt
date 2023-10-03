package com.example.delivery.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery.data.model.Category
import com.example.delivery.data.repository.BasketRepository
import com.example.delivery.data.repository.ProductRepository
import com.example.delivery.domain.model.Product
import com.example.delivery.domain.model.Tag
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
    private var categoryProducts: List<Product> = listOf()
    private var tags: List<Tag> = listOf()

    fun onScreenOpened() {
        viewModelScope.launch {
            products = productRepository.getProducts()
            tags = productRepository.getTags()
            try {
                _viewState.value = _viewState.value?.copy(
                    categories = productRepository.getCategories(),
                    products = products,
                    tags = tags,
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

    fun onCheckedChanged(tag: Tag, checked: Boolean) {
        val tags = _viewState.value?.tags!!.toMutableList()
        tags[tags.indexOf(tag)] = tag.copy(isSelected = checked)
        _viewState.value = _viewState.value?.copy(
            products = if (checked) {
                categoryProducts.filter { product -> product.tagIds.contains(tag.id) }
            } else {
                categoryProducts
            },
            tags = tags,
        )
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
        categoryProducts = products.filter { product -> product.categoryId == category.id }
        _viewState.value = _viewState.value?.copy(
            selectedCategory = category,
            products = categoryProducts,
            tags = tags,
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
}