package com.example.delivery.di.module

import com.example.delivery.data.api.DeliveryApi
import com.example.delivery.data.repository.BasketRepository
import com.example.delivery.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DeliveryRepositoryModule {

    @Provides
    fun provideProductRepository(api: DeliveryApi) = ProductRepository(api)

    @Singleton
    @Provides
    fun provideBasketRepository() = BasketRepository()
}