package com.example.delivery.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.di.util.ViewModelFactory
import com.example.delivery.di.util.ViewModelKey
import com.example.delivery.presentation.viewmodel.BasketViewModel
import com.example.delivery.presentation.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DeliveryViewModelsModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BasketViewModel::class)
    fun bindBasketViewModel(viewModel: BasketViewModel): ViewModel
}