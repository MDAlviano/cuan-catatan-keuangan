package com.cuan.catatankeuangan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cuan.catatankeuangan.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int,
    val activeIcon: Int
) {

    @Composable
    fun getTitle(): String {
        return stringResource(id = when (this) {
            Home -> R.string.home_nav
            Product -> R.string.product_nav
            History -> R.string.history_nav
            Report -> R.string.report_nav
            Profile -> R.string.profile_nav
        })
    }

    object Home : BottomBarScreen(
        route = "home",
        icon = R.drawable.home,
        activeIcon = R.drawable.home_filled
    )

    object Product : BottomBarScreen(
        route = "product",
        icon = R.drawable.shop,
        activeIcon = R.drawable.shop_filled
    )

    object History : BottomBarScreen(
        route = "history",
        icon = R.drawable.wallet2,
        activeIcon = R.drawable.wallet2_filled
    )

    object Report : BottomBarScreen(
        route = "report",
        icon = R.drawable.status,
        activeIcon = R.drawable.status_filled
    )

    object Profile : BottomBarScreen(
        route = "profile",
        icon = R.drawable.profile,
        activeIcon = R.drawable.profile_filled
    )

}