package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3

@Composable
fun PasswordTextFields(passwordValue: String, label: String, hint: String, onPasswordChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var passwordVisibility by remember {
            mutableStateOf(
                false
            )
        }

        val icon = if (passwordVisibility)
            painterResource(id = R.drawable.open_eye)
        else
            painterResource(id = R.drawable.open_eye)

        Column {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = label,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordValue,
                onValueChange = onPasswordChange,
                placeholder = {
                    Text(
                        text = hint,
                        color = OptionalColor3
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = icon,
                            contentDescription = "Trailing Icon",
                            tint = Color.Unspecified
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )
        }
    }
}