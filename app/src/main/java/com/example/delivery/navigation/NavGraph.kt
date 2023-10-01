package com.example.delivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.delivery.presentation.screen.AnimatedSplashScreen
import com.example.delivery.presentation.screen.BasketScreen
import com.example.delivery.presentation.screen.HomeScreen
import com.example.delivery.presentation.screen.ProductCardScreen
import com.example.delivery.presentation.viewmodel.BasketViewModel
import com.example.delivery.presentation.viewmodel.HomeViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    factory: ViewModelProvider.Factory,
) {
    val getVmFactory: () -> ViewModelProvider.Factory = remember {
        { factory }
    }
    val homeViewModel: HomeViewModel = viewModel(factory = getVmFactory())

    NavHost(
        navController = navController, startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel,
            )
        }
        composable(route = Screen.Basket.route) {
            val basketViewModel: BasketViewModel = viewModel(factory = getVmFactory())
            BasketScreen(
                navController,
                viewModel = basketViewModel,
            )
        }
        composable(
            route = Screen.ProductCard.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            ProductCardScreen(
                navController = navController,
                viewModel = homeViewModel,
                productId = backStackEntry.arguments?.getInt("productId"),
            )
        }
    }
}