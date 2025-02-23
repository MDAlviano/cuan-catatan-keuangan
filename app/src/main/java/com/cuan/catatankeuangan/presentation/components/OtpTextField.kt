package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpTextField(
    otpLength: Int = 6,
    otpValue: String,
    onOtpChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        for (i in 0 until otpLength) {
            val char = otpValue.getOrNull(i)?.toString() ?: ""

            OutlinedTextField(
                value = char,
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        val newOtp = otpValue.toMutableList()
                        if (newOtp.size > i) {
                            newOtp[i] = newValue.firstOrNull() ?: ' '
                        } else {
                            newOtp.add(newValue.firstOrNull() ?: ' ')
                        }
                        onOtpChange(newOtp.joinToString(""))

                        // Pindah fokus ke kotak berikutnya jika ada input
                        if (newValue.isNotEmpty() && i < otpLength - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                    } else if (newValue.isEmpty()) {
                        // Hapus karakter kembali ke kotak sebelumnya
                        val newOtp = otpValue.toMutableList().apply { removeAt(i) }
                        onOtpChange(newOtp.joinToString(""))

                        if (i > 0) {
                            focusRequesters[i - 1].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .width(48.dp)
                    .height(80.dp)
                    .focusRequester(focusRequesters[i])
                    .focusable(),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OtpTextFieldPreview() {
    var otp by rememberSaveable { mutableStateOf("") }

    OtpTextField(
        otpValue = otp, onOtpChange = { otp = it }
    )
}