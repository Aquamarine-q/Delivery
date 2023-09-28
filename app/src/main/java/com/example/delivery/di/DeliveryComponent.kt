package com.example.delivery.di

import com.example.delivery.MainActivity
import com.example.delivery.di.module.DeliveryApiModule
import com.example.delivery.di.module.DeliveryRepositoryModule
import com.example.delivery.di.module.DeliveryViewModelsModule
import com.example.delivery.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DeliveryApiModule::class,
        DeliveryRepositoryModule::class,
        NetworkModule::class,
        DeliveryViewModelsModule::class,
    ],
)
interface DeliveryComponent {

    @Component.Builder
    interface Builder {
        fun build(): DeliveryComponent
    }

    fun inject(activity: MainActivity)
}