package com.cuan.catatankeuangan.presentation.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cuan.catatankeuangan.presentation.navigation.BottomBarScreen
import com.cuan.catatankeuangan.presentation.navigation.BottomNavGraph
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(transactionViewModel: TransactionViewModel, productViewModel: ProductViewModel) {
    val navController = rememberNavController()
    var bottomNavHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Product,
        BottomBarScreen.History,
        BottomBarScreen.Report,
        BottomBarScreen.Profile
    )

    val showBottomBar = currentRoute in screens.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(screens, navController, currentDestination) { height ->
                    bottomNavHeight = with(density) { height.toDp() }
                }
            }
        },
        containerColor = Color.White
    ) {
        BottomNavGraph(
            navController = navController,
            bottomNavHeight,
            transactionViewModel = transactionViewModel,
            productViewModel = productViewModel
        )
    }
}

@Composable
fun BottomBar(
    screens: List<BottomBarScreen>,
    navController: NavHostController,
    currentDestination: NavDestination?,
    onHeightChanged: (Int) -> Unit
) {

    NavigationBar(
        containerColor = Color2,
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size -> onHeightChanged(size.height) }
//            .padding(horizontal = 24.dp)
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        label = {
            Text(text = screen.getTitle(), color = Color.White)
        },
        icon = {
            Icon(
                painter = painterResource(if (isSelected) screen.activeIcon else screen.icon),
                contentDescription = "${screen.getTitle()} navigation icon",
                modifier = Modifier.width(35.dp)
            )
        },
        selected = isSelected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}