package com.example.delivery.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery.data.repository.BasketRepository
import com.example.delivery.domain.model.Product
import com.example.delivery.presentation.viewstate.BasketViewState
import javax.inject.Inject

class BasketViewModel
@Inject constructor(
    private val basketRepository: BasketRepository,
) : ViewModel() {

    private val _viewState = MutableLiveData(BasketViewState())
    val viewState: LiveData<BasketViewState> = _viewState

    fun onScreenOpened() {
        _viewState.value = _viewState.value?.copy(
            products = basketRepository.getProducts(),
            cost = basketRepository.getFinalCost(),
        )
    }

    fun onAddBasketItem(product: Product) {
        basketRepository.addProduct(product)
        _viewState.value = _viewState.value?.copy(
            products = basketRepository.getProducts(),
            cost = basketRepository.getFinalCost(),
        )
    }

    fun onRemoveBasketItem(product: Product) {
        basketRepository.removeProduct(product)
        _viewState.value = _viewState.value?.copy(
            products = basketRepository.getProducts(), cost = basketRepository.getFinalCost()
        )
    }
}