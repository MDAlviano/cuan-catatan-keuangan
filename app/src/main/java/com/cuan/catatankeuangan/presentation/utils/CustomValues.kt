package com.cuan.catatankeuangan.presentation.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getCustomTopPadding(extraPadding: Dp = 0.dp): Dp {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    return statusBarHeight + extraPadding
}

@Composable
fun getCustomBottomPadding(extraPadding: Dp = 0.dp): Dp {
    val statusBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    return statusBarHeight + extraPadding
}

