package com.cuan.catatankeuangan.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("EEEE, d/MM/yyyy", Locale("id", "ID"))
    return  formatter.format(Date(timestamp))
}