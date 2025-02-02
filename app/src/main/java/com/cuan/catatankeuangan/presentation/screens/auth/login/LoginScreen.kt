package com.cuan.catatankeuangan.presentation.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.presentation.components.CustomButton
import com.cuan.catatankeuangan.presentation.components.GradientText
import com.cuan.catatankeuangan.presentation.components.OrLine
import com.cuan.catatankeuangan.presentation.components.PasswordTextFields
import com.cuan.catatankeuangan.presentation.components.RedirectText
import com.cuan.catatankeuangan.presentation.components.TextFields
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            modifier = Modifier.weight(1f),
            onClick = { /* TODO */ },
            text = "Masuk"
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
                verticalArrangement = Arrangement.Bottom
            ) {
                GradientText(text = "Selamat Datang Kembali!", fontSize = 35.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Masuk untuk melanjutkan",
                    color = OptionalColor3,
                    fontSize = 17.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .padding()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFields(label = "Email", hint = "your@example.com")
                PasswordTextFields(label = "Password", "********")
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {

                        },
                    text = "Lupa Password?",
                    color = Color3,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.End
                )
                CustomButton(
                    onClick = { /*TODO*/ },
                    text = "Masuk",
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                )
                OrLine(paddingHorizontal = 80.dp, paddingVertical = 5.dp)
                CustomButton(
                    onClick = { /*TODO*/ },
                    text = "Masuk dengan Kode"
                )
                Spacer(modifier = Modifier.height(40.dp))
                RedirectText(text = "Belum punya akun? ", navText = "Daftar", onClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}