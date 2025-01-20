package com.cuan.catatankeuangan.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Product : BottomBarScreen(
        route = "product",
        title = "Product",
        icon = Icons.Default.Home
    )

    object History : BottomBarScreen(
        route = "history",
        title = "History",
        icon = Icons.Default.AccountBox
    )

    object Report : BottomBarScreen(
        route = "report",
        title = "Report",
        icon = Icons.Default.Share
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

}