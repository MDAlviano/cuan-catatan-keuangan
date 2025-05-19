package com.cuan.catatankeuangan.presentation.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.presentation.components.CustomButton
import com.cuan.catatankeuangan.presentation.components.GradientText
import com.cuan.catatankeuangan.presentation.components.OtpTextField
import com.cuan.catatankeuangan.presentation.components.RedirectText
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3

@Composable
@Preview(showBackground = true)
fun VerificationRegisterScreen() {
    var otp by rememberSaveable { mutableStateOf("") }
    val email = "cuankelompok3@gmail.com"
    val context = LocalContext.current

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
                modifier = Modifier.weight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GradientText(text = "Konfirmasi Email", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Masukkan kode yang telah dikirimkan ke",
                    color = OptionalColor3,
                    fontSize = 19.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = email,
                    color = Color1,
                    fontSize = 19.sp
                )
            }
            Column(
                modifier = Modifier
                    .padding()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OtpTextField(
                    otpValue = otp,
                    onOtpChange = { otp = it },
                )
                Spacer(modifier = Modifier.height(80.dp))
                CustomButton(
                    onClick = {
                        if (otp.length <= 6) {
                            Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "You're Sigma!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    text = "Daftar",
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                )
                Spacer(modifier = Modifier.height(30.dp))
                RedirectText(
                    text = "Tidak menerima kode? ",
                    navText = "Kirim Ulang",
                    onClick = {},
                )
            }
        }
    }
}