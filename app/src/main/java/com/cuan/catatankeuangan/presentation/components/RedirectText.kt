package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3

@Composable
fun RedirectText(
    text: String,
    navText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = OptionalColor3
        )
        Text(
            modifier = Modifier.clickable {
                onClick()
            },
            text = navText,
            color = Color3
        )
    }
}
@Preview
@Composable
private fun ToRegisterPrev() {
    RedirectText(
        text = "Belum punya akun? ",
        navText = "Daftar",
        onClick = {
            // do something
        }
    )
}

@Preview
@Composable
private fun ToLoginPrev() {
    RedirectText(
        text = "Sudah punya akun? ",
        navText = "Masuk",
        onClick = {
            // do something
        }
    )
}