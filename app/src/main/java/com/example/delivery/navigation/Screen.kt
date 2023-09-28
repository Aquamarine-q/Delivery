package com.example.delivery.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object Basket : Screen("basket_screen")
    object ProductCard : Screen("product_card_screen")
}