package com.cuan.catatankeuangan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cuan.catatankeuangan.presentation.screens.auth.login.LoginScreen
import com.cuan.catatankeuangan.presentation.screens.auth.register.RegisterScreen
import com.cuan.catatankeuangan.presentation.screens.history.HistoryScreen
import com.cuan.catatankeuangan.presentation.screens.home.HomeScreen
import com.cuan.catatankeuangan.presentation.screens.product.ProductScreen
import com.cuan.catatankeuangan.presentation.screens.profile.ProfileScreen
import com.cuan.catatankeuangan.presentation.screens.report.ReportScreen
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, bottomNavHeight: Dp, transactionViewModel: TransactionViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Product.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(bottomNavHeight, transactionViewModel)
        }
        composable(route = BottomBarScreen.Product.route) {
            ProductScreen(bottomNavHeight)
        }
        composable(route = BottomBarScreen.History.route) {
            HistoryScreen(bottomNavHeight, transactionViewModel)
        }
        composable(route = BottomBarScreen.Report.route) {
            ReportScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navController, bottomNavHeight)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController, bottomNavHeight)
        }
    }
}