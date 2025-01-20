package com.cuan.catatankeuangan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cuan.catatankeuangan.presentation.screens.history.HistoryScreen
import com.cuan.catatankeuangan.presentation.screens.home.HomeScreen
import com.cuan.catatankeuangan.presentation.screens.product.ProductScreen
import com.cuan.catatankeuangan.presentation.screens.profile.ProfileScreen
import com.cuan.catatankeuangan.presentation.screens.report.ReportScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Product.route) {
            ProductScreen()
        }
        composable(route = BottomBarScreen.History.route) {
            HistoryScreen()
        }
        composable(route = BottomBarScreen.Report.route) {
            ReportScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }
}