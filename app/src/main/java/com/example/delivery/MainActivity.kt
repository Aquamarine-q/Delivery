package com.example.delivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.delivery.navigation.SetupNavGraph
import com.example.delivery.presentation.app.DeliveryApp
import com.example.delivery.ui.theme.DeliveryTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContent {
            DeliveryTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    factory = factory,
                )
            }
        }
    }

    fun inject() {
        DeliveryApp.appComponent.inject(this)
    }
}