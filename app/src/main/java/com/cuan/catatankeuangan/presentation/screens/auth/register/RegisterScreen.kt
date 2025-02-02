package com.cuan.catatankeuangan.presentation.screens.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.R
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
fun RegisterScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            modifier = Modifier.weight(1f),
            onClick = { /* TODO */ },
            text = "Daftar"
        )
        Column(
            modifier = Modifier
                .weight(5f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                GradientText(text = "Halo!", fontSize = 35.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Buat akun untuk melanjutkan",
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
                TextFields(label = "Nama", hint = "Orang Cakep")
                TextFields(label = "Email", hint = "your@example.com")
                PasswordTextFields(label = "Password", "********")
                PasswordTextFields(label = "Konfirmasi Password", "********")
                Spacer(modifier = Modifier.height(24.dp))
                CustomButton(
                    onClick = { /*TODO*/ },
                    text = "Konfirmasi",
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                )
                Spacer(modifier = Modifier.height(30.dp))
                RedirectText(text = "Sudah punya akun? ", navText = "Masuk", onClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}