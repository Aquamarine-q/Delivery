package com.example.delivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.delivery.presentation.screen.AnimatedSplashScreen
import com.example.delivery.presentation.screen.BasketScreen
import com.example.delivery.presentation.screen.HomeScreen
import com.example.delivery.presentation.screen.ProductCardScreen

@Composable
fun SetupNavGraph(navController: NavHostController, factory: ViewModelProvider.Factory) {
    val getVmFactory: () -> ViewModelProvider.Factory = remember {
        { factory }
    }

    NavHost(
        navController = navController, startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModelFactory = getVmFactory)
        }
        composable(route = Screen.Basket.route) {
            BasketScreen(navController)
        }
        composable(
            route = Screen.ProductCard.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProductCardScreen(
                navController = navController,
                productId = backStackEntry.arguments?.getString("productId"),
                viewModelFactory = getVmFactory,
            )
        }
    }
}