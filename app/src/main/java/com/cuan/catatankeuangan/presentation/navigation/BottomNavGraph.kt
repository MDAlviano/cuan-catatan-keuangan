package com.cuan.catatankeuangan.presentation.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(bottomNavHeight, transactionViewModel)
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