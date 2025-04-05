package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import com.cuan.catatankeuangan.presentation.utils.formatInputNominal


// iya saya tau ini dobel tapi yaudah lah ya hehe
@Composable
fun CustomTextField(
    label: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        color = Color2,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.height(2.dp))
    OutlinedTextField(
        value = fieldValue,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, color = OptionalColor3) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = OptionalColor3,
            focusedBorderColor = Color2
        ),
        modifier = Modifier.fillMaxWidth().then(modifier)
    )
}

@Composable
fun CurrencyTextField(
    label: String,
    fieldValue: TextFieldValue,
    rawValue: MutableState<String>,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        color = Color2,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
    OutlinedTextField(
        value = fieldValue,
        onValueChange = { newValue ->
            onValueChange(formatInputNominal(newValue, rawValue))
        },
        placeholder = {
            Text(
                text = "0",
                color = OptionalColor3,
                fontFamily = outfitFamily,
                fontSize = 18.sp
            )
        },
        leadingIcon = {
            Text(
                text = "Rp",
                fontWeight = FontWeight.SemiBold,
                color = Color2
            )
        },
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = OptionalColor3,
            focusedBorderColor = Color2
        ),
        textStyle = TextStyle(color = Color2, fontFamily = outfitFamily, fontSize = 18.sp),
        modifier = Modifier.fillMaxWidth().then(modifier)
    )
}



