package com.example.delivery.presentation.app

import android.app.Application
import com.example.delivery.di.DaggerDeliveryComponent
import com.example.delivery.di.DeliveryComponent

class DeliveryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerDeliveryComponent.builder().build()
    }

    companion object {
        lateinit var appComponent: DeliveryComponent
    }
}