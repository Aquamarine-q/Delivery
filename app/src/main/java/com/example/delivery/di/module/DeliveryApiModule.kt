package com.example.delivery.di.module

import com.example.delivery.data.api.DeliveryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DeliveryApiModule {

    @Provides
    fun provideDeliveryApi(retrofit: Retrofit): DeliveryApi {
        return retrofit.create(DeliveryApi::class.java)
    }
}