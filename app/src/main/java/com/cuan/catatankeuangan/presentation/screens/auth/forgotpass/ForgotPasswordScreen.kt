package com.cuan.catatankeuangan.presentation.screens.auth.forgotpass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.presentation.components.CustomButton
import com.cuan.catatankeuangan.presentation.components.GradientText
import com.cuan.catatankeuangan.presentation.components.TextFields
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3

@Composable
fun ForgotPasswordScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            modifier = Modifier.weight(1f),
            onClick = { /* TODO */ },
            text = "Lupa Password"
        )
        Column(
            modifier = Modifier
                .weight(5f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GradientText(text = "Pemulihan Password", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Masukkan email akun anda",
                    color = OptionalColor3,
                    fontSize = 17.sp
                )
            }
            Column(
                modifier = Modifier
                    .padding()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFields(label = "Email", hint = "your@example.com")
                Spacer(modifier = Modifier.height(80.dp))
                CustomButton(
                    onClick = { /*TODO*/ },
                    text = "Pulihkan Password"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}