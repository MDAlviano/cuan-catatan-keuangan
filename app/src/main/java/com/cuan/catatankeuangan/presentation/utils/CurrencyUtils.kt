package com.cuan.catatankeuangan.presentation.utils

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