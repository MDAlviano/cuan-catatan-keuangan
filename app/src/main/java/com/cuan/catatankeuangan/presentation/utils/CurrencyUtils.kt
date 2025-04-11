package com.cuan.catatankeuangan.presentation.utils

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatAsCurrency(amount: Long): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.currency = Currency.getInstance("IDR")
    format.maximumFractionDigits = 0
    return format.format(amount)
}

fun formatNominal(amount: Long): String {
    val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
        groupingSeparator = '.'
    }
    return DecimalFormat("#,###", symbols).format(amount)
}

fun formatInputNominal(newValue: TextFieldValue, rawInputState: MutableState<String>): TextFieldValue {
    val rawInput = newValue.text.filter { it.isDigit() }
    rawInputState.value = rawInput

    val formattedText = if (rawInput.isNotEmpty()) formatNominal(rawInput.toLong()) else ""

    val cursorOffset = newValue.selection.start + (formattedText.length - newValue.text.length)

    return newValue.copy(
        text = formattedText,
        selection = TextRange(cursorOffset.coerceIn(0, formattedText.length))
    )
}

fun formatAbbreviatedNominal(value: Long): String {
    val symbols = DecimalFormatSymbols(Locale("in", "ID")).apply {
        decimalSeparator = ','
    }

    val formatter = DecimalFormat("#.##", symbols)

    return when {
        value >= 1_000_000_000 -> "${formatter.format(value / 1_000_000_000.0)} M"
        value >= 1_000_000 -> "${formatter.format(value / 1_000_000.0)} jt"
        value >= 1_000 -> formatNominal(value)
        else -> value.toString()
    }
}