package com.cuan.catatankeuangan.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun DatePickerField(
    modifier: Modifier,
    placeholder: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = remember {
        DatePickerDialog(context, { _, y, m, d ->
            onDateSelected(LocalDate.of(y, m + 1, d))
        }, year, month, day)
    }

    Button  (
        modifier = modifier,
        onClick = {
            datePickerDialog.show()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = date?.format(formatter) ?: placeholder,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}