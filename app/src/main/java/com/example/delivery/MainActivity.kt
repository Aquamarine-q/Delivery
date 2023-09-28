package com.example.delivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                SetupNavGraph(navController = navController, factory = factory)
            }
        }
    }

    fun inject() {
        DeliveryApp.appComponent.inject(this)
    }
}